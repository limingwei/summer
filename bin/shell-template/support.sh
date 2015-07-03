# ***abc***-support.sh
# limingwei
# 2014-07-23 15:51:33

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
        echo "doc"
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

    elif [ $1 = "doc" ]
    then
        _doc_ $@

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
    mvn test -Dtest=cn.limw.summer.AllTest
}

function _stop_()
{
    echo "stop ***abc***-support for noting"
}

#
# install 时候不再 update, 需另外再调用
#
function _install_()
{
    _clean_

    echo "install ***abc***-support"

    cd ${source_code_root}/***abc***-support/
    mvn install -Dmaven.test.skip=true -Dmaven.javadoc.skip=true

    ***abc*** svninfo ***abc***-support
    echo "***abc***-support installed";
}

function _run_()
{
    echo "run ***abc***-support for noting"
}

function _clean_()
{
    echo "clean ***abc***-support"
    cd ${source_code_root}/***abc***-support/
    mvn clean
}

function _update_()
{
    echo "update ***abc***-support"
    cd ${source_code_root}/***abc***-support/
    svn update --force
}

function _doc_()
{
    echo "doc ***abc***-support"

    rm -rf ${www_root}/java-doc/***abc***-support
    mkdir -p ${www_root}/java-doc/***abc***-support
    echo "removing old docs of ***abc***-support"

    cd ${source_code_root}/***abc***-support/
    mvn install -Dmaven.test.skip=true -Dmaven.javadoc.skip=false

    cp ${source_code_root}/***abc***-support/target/***abc***-support-0.0.1-SNAPSHOT-javadoc.jar ${www_root}/java-doc/***abc***-support/***abc***-support-0.0.1-SNAPSHOT-javadoc.jar
    cd ${www_root}/java-doc/***abc***-support/
    unzip ***abc***-support-0.0.1-SNAPSHOT-javadoc.jar
}

_main_ $@