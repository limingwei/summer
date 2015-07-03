# ***abc***-push.sh
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
    cd ${source_code_root}/***abc***-support/
    mvn test -Dtest=com.***abc***.push.AllTest
}

function _stop_()
{
    echo "stoping ***abc***-push on port ${running_ports['***abc***-push']}"

    ***abc*** kill_port ${running_ports['***abc***-push']}

    echo "stoped ***abc***-push on port ${running_ports['***abc***-push']}" >${shell_root}/project-status/***abc***-push
}

#
# install 时候不再 update, 需另外再调用
#
function _install_()
{
    _clean_

    echo "before install" >${shell_root}/project-status/***abc***-push
    echo "install ***abc***-push"

    cd ${source_code_root}/***abc***-push/
    mvn compile
    mvn package -Dmaven.test.skip=true
    rm -rf ${www_root}/***abc***-push
    mkdir -p ${www_root}/***abc***-push

    ***abc*** svninfo ***abc***-push
    echo "***abc***-push installed"
    echo "installed" >${shell_root}/project-status/***abc***-push
}

function _run_()
{
    echo "before run" >${shell_root}/project-status/***abc***-push

    cp ${source_code_root}/***abc***-push/target/***abc***-push.war ${www_root}/***abc***-push/***abc***-push.war
    cd ${www_root}/***abc***-push/
    unzip ***abc***-push.war
    rm -rf ***abc***-push.war

    cd ${www_root}/***abc***-push/WEB-INF/classes
    rm -rf config.properties
    cp "${conf_root}/***abc***-push-config.properties" "config.properties"

    cd ${app_root}/jetty-***abc***-push/
    java -jar start.jar -Dorg.eclipse.jetty.util.URI.charset=UTF-8 -Dorg.eclipse.jetty.server.Request.maxFormContentSize=-1 -Djetty.port=${running_ports['***abc***-push']} &

    echo "started" >${shell_root}/project-status/***abc***-push
}

function _clean_()
{
    echo "before clean" >${shell_root}/project-status/***abc***-push

    echo "clean ***abc***-push"
    cd ${source_code_root}/***abc***-push/
    mvn clean

    echo "cleaned" >${shell_root}/project-status/***abc***-push
}

function _update_()
{
    echo "before update" >${shell_root}/project-status/***abc***-push
    echo "update ***abc***-push"

    cd ${source_code_root}/***abc***-push/
    svn update --force

    echo "updated" >${shell_root}/project-status/***abc***-push
}

_main_ $@
