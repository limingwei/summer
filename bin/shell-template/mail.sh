# ***abc***-mail.sh
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
        _stop_

    elif [ $1 = "install" ]
    then
        _install_

    elif [ $1 = "run" ]
    then
        _run_

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
    cd ${source_code_root}/***abc***-mail/
    mvn test -Dtest=com.***abc***.mail.AllTest
}

function _stop_()
{
    echo "stoping ***abc***-mail on port ${running_ports['***abc***-mail']}"
    ***abc*** kill_port ${running_ports['***abc***-mail']}

    echo "stoped" >${shell_root}/project-status/***abc***-mail
}

#
# install
#
function _install_()
{
    _clean_

    echo "before install" >${shell_root}/project-status/***abc***-mail
    echo "install ***abc***-mail"

    cd ${source_code_root}/***abc***-mail/
    mvn install -Dmaven.test.skip=true

    ***abc*** svninfo ***abc***-mail
    echo "***abc***-mail installed";
    echo "installed" >${shell_root}/project-status/***abc***-mail
}

function _run_()
{
    echo "before run" >${shell_root}/project-status/***abc***-mail
    echo "run ***abc***-mail"

    rm -rf ${www_root}/***abc***-mail
    mkdir -p ${www_root}/***abc***-mail
    cp ${source_code_root}/***abc***-mail/target/***abc***-mail-0.0.1-SNAPSHOT.jar ${www_root}/***abc***-mail/***abc***-mail-0.0.1-SNAPSHOT.jar

    cd ${source_code_root}/***abc***-mail/
    mvn dependency:copy-dependencies -DoutputDirectory=${www_root}/***abc***-mail/lib

    cp -f ${conf_root}/***abc***-mail-config.properties ${www_root}/***abc***-mail/config.properties

    java -jar ${www_root}/***abc***-mail/***abc***-mail-0.0.1-SNAPSHOT.jar &

    echo "started" >${shell_root}/project-status/***abc***-mail
}

function _clean_()
{
    echo "before clean" >${shell_root}/project-status/***abc***-mail
    echo "clean ***abc***-mail"

    cd ${source_code_root}/***abc***-mail/
    mvn clean

    echo "cleaned" >${shell_root}/project-status/***abc***-mail
}

function _update_()
{
    echo "before update" >${shell_root}/project-status/***abc***-mail
    echo "update ***abc***-mail"

    cd ${source_code_root}/***abc***-mail/
    svn update --force

    echo "updated" >${shell_root}/project-status/***abc***-mail
}

_main_ $@
