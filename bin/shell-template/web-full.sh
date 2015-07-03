# ***abc***-web-full.sh
# limingwei
# 2015-02-10 14:03:07

# main
function _main_()
{
    if [ $# -eq 0 ]
    then
        echo "stop"
        echo "install"
        echo "run"
        echo "clean"
        echo "update"
        echo "test"

    elif [ $1 = "stop" ]
    then
        _stop_ $@

    elif [ $1 = "install" ]
    then
        _install_ $@

    elif [ $1 = "run" ]
    then
        _run_ $@

    elif [ $1 = "clean" ]
    then
        _clean_ $@

    elif [ $1 = "update" ]
    then
        _update_ $@

    elif [ $1 = "test" ]
    then
        _test_ $@

    else
        _main_
    fi
}

function _test_()
{
    echo "***abc***-web-full test for nothing"
}

function _stop_()
{
    echo "stoping ***abc***-web-full on port ${running_ports['***abc***-web-full']}"
    ***abc*** kill_port ${running_ports['***abc***-web-full']}

    echo "stoped" >${shell_root}/project-status/***abc***-web-full
}

#
# install 时候不再 update, 需另外再调用
#
function _install_()
{
    _clean_

    echo "delete files in ${app_root}/tomcat-***abc***-web-full/webapps/ROOT"
    rm -rf ${app_root}/tomcat-***abc***-web-full/webapps/ROOT

    echo "before install" >${shell_root}/project-status/***abc***-web-full
    echo "install ***abc***-web-full"

    cd ${source_code_root}/***abc***-web/
    mvn compile
    mvn package -Dmaven.test.skip=true

    ***abc*** svninfo ***abc***-web
    echo "***abc***-web-full installed";
    echo "installed" >${shell_root}/project-status/***abc***-web-full
}

function _run_()
{
    echo "before run" >${shell_root}/project-status/***abc***-web-full
    echo "run ***abc***-web-full"

    rm -rf ${www_root}/***abc***-web-full
    mkdir -p ${www_root}/***abc***-web-full
    cp ${source_code_root}/***abc***-web/target/***abc***-web.war ${www_root}/***abc***-web-full/***abc***-web-full.war
    cd ${www_root}/***abc***-web-full/
    unzip ***abc***-web-full.war

    cd ${source_code_root}/***abc***-account-service/
    mvn dependency:copy-dependencies -DoutputDirectory=${www_root}/***abc***-web-full/WEB-INF/lib

    cd ${source_code_root}/***abc***-open/
    mvn dependency:copy-dependencies -DoutputDirectory=${www_root}/***abc***-web-full/WEB-INF/lib

    cp "${source_code_root}/***abc***-account-service/target/***abc***-account-service-0.0.1-SNAPSHOT.jar" "${www_root}/***abc***-web-full/WEB-INF/lib/***abc***-account-service-0.0.1-SNAPSHOT.jar"
    cp "${source_code_root}/***abc***-ticket-service/target/***abc***-ticket-service-0.0.1-SNAPSHOT.jar" "${www_root}/***abc***-web-full/WEB-INF/lib/***abc***-ticket-service-0.0.1-SNAPSHOT.jar"
    cp "${source_code_root}/***abc***-helpcenter-service/target/***abc***-helpcenter-service-0.0.1-SNAPSHOT.jar" "${www_root}/***abc***-web-full/WEB-INF/lib/***abc***-helpcenter-service-0.0.1-SNAPSHOT.jar"
    cp "${source_code_root}/***abc***-weixin-service/target/***abc***-weixin-service-0.0.1-SNAPSHOT.jar" "${www_root}/***abc***-web-full/WEB-INF/lib/***abc***-weixin-service-0.0.1-SNAPSHOT.jar"
    cp "${source_code_root}/***abc***-mail/target/***abc***-mail-0.0.1-SNAPSHOT.jar" "${www_root}/***abc***-web-full/WEB-INF/lib/***abc***-mail-0.0.1-SNAPSHOT.jar"

    cd ${www_root}/***abc***-web-full/WEB-INF/classes/dev
    rm -rf dev-config.properties
    cp -f "${conf_root}/***abc***-web-full-config.properties" "dev-config.properties"

    cd ${www_root}/***abc***-web-full/WEB-INF/classes
    rm -rf config.properties
    cp -f "${conf_root}/***abc***-web-full-config.properties" "config.properties"

    cd ${www_root}/***abc***-web-full/WEB-INF/classes
    rm -rf spring.xml
    cp "${conf_root}/***abc***-web-full-spring.xml" "spring.xml"

    cd ${www_root}/***abc***-web-full/WEB-INF/classes
    rm -rf log4j.xml
    cp "${conf_root}/***abc***-web-full-log4j.xml" "log4j.xml"

    # project config files
    cd ${source_code_root}/***abc***-support/
    mvn test -Dtest=com.***abc***.setup.***abc***CopyOpenToWeb

#    cd ${app_root}/tomcat-***abc***-web-full/bin/
#    sh catalina.sh run &

    cd ${app_root}/jetty-***abc***-web-full/
    java -Xrs -server -verbosegc -Xcheck:jni -verbose:jni -Xms1000m -Xmx1000m -Xmn300m -XX:PermSize=400m -XX:MaxPermSize=400m -Xss512k -XX:+DisableExplicitGC -XX:SurvivorRatio=8 -XX:MaxTenuringThreshold=15 -XX:+UseConcMarkSweepGC -XX:+UseParNewGC -XX:+CMSParallelRemarkEnabled -XX:+UseCMSCompactAtFullCollection -XX:CMSFullGCsBeforeCompaction=0 -XX:+CMSClassUnloadingEnabled -XX:LargePageSizeInBytes=128M -XX:+UseFastAccessorMethods -XX:+UseCMSInitiatingOccupancyOnly -XX:CMSInitiatingOccupancyFraction=70 -XX:SoftRefLRUPolicyMSPerMB=0 -XX:-ReduceInitialCardMarks -XX:+PrintClassHistogram -XX:+PrintGCDetails -XX:+PrintGCTimeStamps -XX:+PrintHeapAtGC -Xloggc:gc.log -Dsun.rmi.dgc.client.gcInterval=3600000 -Dsun.rmi.dgc.server.gcInterval=3600000 -jar start.jar -Dorg.eclipse.jetty.util.URI.charset=UTF-8 -Dorg.eclipse.jetty.server.Request.maxFormContentSize=-1 -Djetty.port=${running_ports['***abc***-web-full']} &

    echo "***abc***-web-full started"
    echo "started" >${shell_root}/project-status/***abc***-web-full
}

function _clean_()
{
    echo "cleaned" >${shell_root}/project-status/***abc***-web-full
}

function _update_()
{
    echo "before update" >${shell_root}/project-status/***abc***-web-full
    echo "update ***abc***-web"

    cd ${source_code_root}/***abc***-web/
    svn update --force

    echo "updated" >${shell_root}/project-status/***abc***-web-full
}

_main_ $@