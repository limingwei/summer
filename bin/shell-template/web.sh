# ***abc***-web.sh
# limingwei
# 2014-07-23 15:45:30

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
    cd ${source_code_root}/***abc***-web/
    mvn test -Dtest=com.***abc***.web.AllTest
}

function _stop_()
{
    echo "stoping ***abc***-web on port ${running_ports['***abc***-web']}"
    ***abc*** kill_port ${running_ports['***abc***-web']}

    echo "stoped" >${shell_root}/project-status/***abc***-web
}

#
# install 时候不再 update, 需另外再调用
#
function _install_()
{
    _clean_

    echo "delete files in ${app_root}/tomcat-***abc***-web/webapps/ROOT"
    rm -rf ${app_root}/tomcat-***abc***-web/webapps/ROOT

    echo "before install" >${shell_root}/project-status/***abc***-web
    echo "install ***abc***-web"

    cd ${source_code_root}/***abc***-web/
    mvn compile
    mvn package -Dmaven.test.skip=true

    ***abc*** svninfo ***abc***-web
    echo "***abc***-web installed";
    echo "installed" >${shell_root}/project-status/***abc***-web
}

function _run_()
{
    echo "before run" >${shell_root}/project-status/***abc***-web
    echo "run ***abc***-web"

    rm -rf ${www_root}/***abc***-web
    mkdir -p ${www_root}/***abc***-web
    cp ${source_code_root}/***abc***-web/target/***abc***-web.war ${www_root}/***abc***-web/***abc***-web.war
    cd ${www_root}/***abc***-web/
    unzip ***abc***-web.war

    cd ${www_root}/***abc***-web/WEB-INF/classes
    rm -rf config.properties
    cp "${conf_root}/***abc***-web-config.properties" "config.properties"

#	export CATALINA_OPTS="-Xrs -server -verbosegc -Xcheck:jni -verbose:jni -Xms2000m -Xmx2000m -Xmn400m -XX:PermSize=400m -XX:MaxPermSize=400m -Xss512k -XX:+DisableExplicitGC -XX:SurvivorRatio=8 -XX:MaxTenuringThreshold=15 -XX:+UseConcMarkSweepGC -XX:+UseParNewGC -XX:+CMSParallelRemarkEnabled -XX:+UseCMSCompactAtFullCollection -XX:CMSFullGCsBeforeCompaction=1 -XX:+CMSClassUnloadingEnabled -XX:LargePageSizeInBytes=128M -XX:+UseFastAccessorMethods -XX:+UseCMSInitiatingOccupancyOnly -XX:CMSInitiatingOccupancyFraction=75 -XX:SoftRefLRUPolicyMSPerMB=0 -XX:-ReduceInitialCardMarks -XX:+PrintClassHistogram -XX:+PrintGCDetails -XX:+PrintGCTimeStamps -XX:+PrintHeapAtGC -Xloggc:gc.log -Dsun.rmi.dgc.client.gcInterval=3600000 -Dsun.rmi.dgc.server.gcInterval=3600000"

    cd ${app_root}/tomcat-***abc***-web/bin/
    sh catalina.sh run &

    echo "***abc***-web started"
    echo "started" >${shell_root}/project-status/***abc***-web
}

function _clean_()
{
    echo "before clean" >${shell_root}/project-status/***abc***-web

    echo "clean ***abc***-web"
    cd ${source_code_root}/***abc***-web/
    mvn clean

    echo "cleaned" >${shell_root}/project-status/***abc***-web
}

function _update_()
{
    echo "before update" >${shell_root}/project-status/***abc***-web
    echo "update ***abc***-web"

    cd ${source_code_root}/***abc***-web/
    svn update --force

    echo "updated" >${shell_root}/project-status/***abc***-web
}

_main_ $@