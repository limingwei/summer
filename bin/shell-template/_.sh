#!/bin/sh

#
# ***abc***.sh
# limingwei
# 2014-07-23 17:48:54 
#

# init something
projects_str="<#list projects as project><#if project_index gt 0>,</#if>${project}</#list>"  # all projects
projects=(${'$'}{projects_str//,/ })
# init end


#
# 入口函数
#
function _main_()
{
    if [ $# -eq 0 ]
    then
        echo "***abc*** stop"
        echo "***abc*** install = clean && install"
        echo "***abc*** test = maven test"
        echo "***abc*** run"
        echo "***abc*** clean"
        echo "***abc*** update"
        echo "***abc*** status"
        echo "***abc*** sir = stop && update && install && run"
        echo "***abc*** doc"
        echo "***abc*** route"
        echo "shell script for ${target_server_name}"

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

    elif [ $1 = "status" ]
    then
        _status_ $@
    
    elif [ $1 = "sir" ]
    then
        _sir_ $@

    elif [ $1 = "doc" ]
    then
        _doc_ $@

    elif [ $1 = "route" ]
    then
        _route_ $@

    elif [ $1 = "download" ]
    then
        _download_ $@

    elif [ $1 = "init" ]
    then
        _init_ $@

    elif [ $1 = "kill_port" ]
    then
        _kill_port_ $@

    elif [ $1 = "test" ]
    then
        _test_ $@

    elif [ $1 = "upto" ]
    then
        _update_to_version_ $@

    elif [ $1 = "svninfo" ]
    then
        _svn_info_ $@

    else
        _main_
    fi
}

#
# 更新到指定版本
#
function _update_to_version_()
{
    if [ $# -eq 1 ]
    then
        echo "***abc*** upto version"
    else
        for project in ${'$'}{projects[${'@'}]}
        do
            cd ${source_code_root}/${'$'}{project}/
            echo "updating ${'$'}{project}"
            svn up -r $2
        done
    fi
}

#
# svninfo
#
function _svn_info_()
{
    if [ $# -eq 1 ]
    then
        echo "***abc*** svninfo all"
        for project in ${'$'}{projects[${'@'}]}
        do
            echo "***abc*** svninfo ${'$'}{project}"
        done

    elif [ $2 = "all" ]
    then
        for project in ${'$'}{projects[${'@'}]}
        do
            ***abc*** svninfo ${'$'}{project}
        done
    else
        mkdir -p /home/***abc***/tmp/
        cd /home/***abc***/source-code/$2/
        svn info>/home/***abc***/tmp/svn-info-$2
        project_name_str=$(printf "%-25s" "${'$'}2")
        echo -e "${'$'}{project_name_str}\c"
        awk -v var="" -F": " 'NR==5||NR==8||NR==9||NR==10{ var=var"   "${'$'}2; if( ${'$'}0 ~/Last Changed Date/ ){ print var; } }' /home/***abc***/tmp/svn-info-${'$'}2
    fi
}

#
# 停止
#
function _stop_()
{
    if [ $# -eq 1 ]
    then
        echo "***abc*** stop all"
        for project in ${'$'}{projects[${'@'}]}
        do
            echo "***abc*** stop ${'$'}{project}"
        done

    elif [ $2 = "all" ]
    then
        for project in ${'$'}{projects[${'@'}]}
        do
            sh ${shell_root}/${'$'}{project}.sh stop
        done
    else
        sh ${shell_root}/$2.sh stop
    fi
}

#
# maven test
#
function _test_()
{
    if [ $# -eq 1 ]
    then
        echo "***abc*** test all"
        for project in ${'$'}{projects[${'@'}]}
        do
            echo "***abc*** test ${'$'}{project}"
        done

    elif [ $2 = "all" ]
    then
        for project in ${'$'}{projects[${'@'}]}
        do
            sh ${shell_root}/${'$'}{project}.sh test
        done
    else
        sh ${shell_root}/$2.sh test
    fi
}

#
# maven install
#
function _install_()
{
    if [ $# -eq 1 ]
    then
        echo "***abc*** install all"
        for project in ${'$'}{projects[${'@'}]}
        do
            echo "***abc*** install ${'$'}{project}"
        done

    elif [ $2 = "all" ]
    then
        for project in ${'$'}{projects[${'@'}]}
        do
            sh ${shell_root}/${'$'}{project}.sh install
        done
    else
        sh ${shell_root}/$2.sh install
    fi
}

#
# 运行
#
function _run_()
{
    if [ $# -eq 1 ]
    then
        echo "***abc*** run all"
        for project in ${'$'}{projects[${'@'}]}
        do
            echo "***abc*** run ${'$'}{project}"
        done

    elif [ $2 = "all" ]
    then
        for project in ${'$'}{projects[${'@'}]}
        do
            sh ${shell_root}/${'$'}{project}.sh run
        done
    else
        sh ${shell_root}/$2.sh run
    fi
}

#
# maven clean
#
function _clean_()
{
    if [ $# -eq 1 ]
    then
        echo "***abc*** clean all"
        for project in ${'$'}{projects[${'@'}]}
        do
            echo "***abc*** clean ${'$'}{project}"
        done

    elif [ $2 = "all" ]
    then
        for project in ${'$'}{projects[${'@'}]}
        do
            sh ${shell_root}/${'$'}{project}.sh clean
        done
    else
        sh ${shell_root}/$2.sh clean
    fi
}

#
# svn update
#
function _update_()
{
    if [ $# -eq 1 ]
    then
        echo "***abc*** update all"
        for project in ${'$'}{projects[${'@'}]}
        do
            echo "***abc*** update ${'$'}{project}"
        done

    elif [ $2 = "all" ]
    then
        for project in ${'$'}{projects[${'@'}]}
        do
            sh ${shell_root}/${'$'}{project}.sh update
        done
    else
        sh ${shell_root}/$2.sh update
    fi
}

#
# 杀死占用指定端口的程序
#
function _kill_port_()
{
    PORT=$2
    PID=`netstat -tlnp|grep $PORT|awk  '{print $7}'|awk -F '/' '{print $1}'`

    if [ -n "$PID" ]; then
        echo "   killing... port=$PORT, pid=$PID"
        kill $PID

        COUNT=0
        TIMER=0
        while [ $COUNT -lt 1 ]; do
            sleep 1
            COUNT=1
            TIMER=$(($TIMER+1))
            echo -e "   killing... port=$PORT, pid=$PID, timer=$TIMER, count=$COUNT"

            PID_EXIST=`ps --no-heading -p $PID`
            if [ -n "$PID_EXIST" ]; then
                COUNT=0
                TIMER=0
                break
            fi
        done
        echo "   killed... port=$PORT, pid=$PID"
    else
        echo "   not killing... port=$PORT, pid=$PID"
    fi
}

#
# 状态检查
#
function _status_()
{
    _check_status_write_file_ ${running_ports['***abc***-account-service']} ***abc***-account-service
    _check_status_write_file_ ${running_ports['***abc***-ticket-service']} ***abc***-ticket-service
    _check_status_write_file_ ${running_ports['***abc***-helpcenter-service']} ***abc***-helpcenter-service
    _check_status_write_file_ ${running_ports['***abc***-mail']} ***abc***-mail
    _check_status_write_file_ ${running_ports['***abc***-weixin-service']} ***abc***-weixin-service
    _check_status_write_file_ ${running_ports['***abc***-push']} ***abc***-push
    _check_status_write_file_ ${running_ports['***abc***-admin']} ***abc***-admin
    _check_status_write_file_ ${running_ports['***abc***-open']} ***abc***-open
    _check_status_write_file_ ${running_ports['***abc***-web']} ***abc***-web
    _check_status_write_file_ ${running_ports['***abc***-web-full']} ***abc***-web-full

    echo "nginx_route_status"
    cat ${shell_root}/project-status/nginx_route_status
}

#
# 检查单个项目运行状态
#
function _check_status_write_file_()
{
    echo "$2 on port $1"

#    pIDa=`/usr/sbin/lsof -i :$1|grep -v "PID" | awk '{print $2}'`
    pIDa=$(netstat -tlnp|grep $1|awk  '{print $7}'|awk -F '/' '{print $1}')
    if [ "$pIDa" != "" ];
    then
       echo "on" >${shell_root}/project-status/$2
    else
       echo "off" >${shell_root}/project-status/$2
    fi

    cat ${shell_root}/project-status/$2
}

#
# 生成javadoc
#
function _doc_()
{
    if [ $# -eq 1 ]
    then
        echo "***abc*** doc all"
        for project in ${'$'}{projects[${'@'}]}
        do
            echo "***abc*** doc ${'$'}{project}"
        done

    elif [ $2 = "all" ]
    then
        for project in ${'$'}{projects[${'@'}]}
        do
            sh ***abc*** doc ${'$'}{project}
        done

    elif [ $2 = "***abc***-support" ]
    then
        sh ${shell_root}/***abc***-support.sh doc

    else
            _generate_javadoc_ $2
    fi
    echo "done"
}

#
# 生成单个项目javadoc
#
function _generate_javadoc_()
{
    rm -rf /home/***abc***/www/java-doc/$1
    mkdir -p /home/***abc***/www/java-doc/$1
    javadoc -d /home/***abc***/www/java-doc/$1 -sourcepath /home/***abc***/source-code/$1/src/main/java -subpackages com -encoding UTF-8 -charset UTF-8 -package -version -author -use -quiet -windowtitle $1 -doctitle "<h1>$1</h1>" -footer "<h1>$1</h1>" -top "<!--top-->" -bottom "<!--bottom-->" -keywords -linksource
}

#
# 停止 重新部署 启动 
#
function _sir_()
{
    if [ $# -eq 1 ]
    then
        echo "***abc*** sir all"
        for project in ${'$'}{projects[${'@'}]}
        do
            echo "***abc*** sir ${'$'}{project}"
        done
    else
        _stop_ $@
        _update_ $@
        _install_ $@
        _run_ $@
    fi
}

#
# 切换nginx指向
#
function _route_()
{
    if [ $# -eq 1 ]
    then
        echo "arg missing, please use file name in /***abc***-support/bin/nginx-conf, the current value is "
        cat ${shell_root}/project-status/nginx_route_status
        find ${source_code_root}/***abc***-support/bin/nginx-conf -name "*.conf"
    else
        _change_nginx_route_to_ $2
    fi
}

#
# 切换nginx指向
#
function _change_nginx_route_to_()
{
    cp -f ${source_code_root}/***abc***-support/bin/nginx-conf/$1.conf ${app_root}/nginx/conf/nginx.conf
    cd ${app_root}/nginx/sbin
    ./nginx -s reload
    echo "nginx is using the config file ${source_code_root}/***abc***-support/bin/nginx-conf/$1.conf"
    echo $1 >${shell_root}/project-status/nginx_route_status
}

#
# 调用入口方法
#
_main_ $@