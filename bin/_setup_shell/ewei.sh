#!/bin/sh
#
# server setup
# limingwei
# 2015-04-09 16:32:27
#

#
# 入口函数
#
function _main_()
{
	if [[ $# > 0 ]] && [[ $1 = "setup" ]]
	then
        _setup_ $@

	elif [ -e "/home/***/bin/***_project" ]
	then
		sh /home/***/bin/***_project $@

	else
		_setup_ setup
    fi
}

function _setup_()
{
	if [ $# -eq 1 ] # 只有一个参数
    then
        echo "1. *** setup server"
        echo "2. *** setup project"
        echo "3. *** setup config"
        echo "4. *** setup redis"
        echo "5. *** setup nginx"

    elif [ $2 = "server" ]
    then
		sh /home/***/bin/***_server_setup.sh

    elif [ $2 = "project" ]
    then
		sh /home/***/bin/***_project_setup.sh

    elif [ $2 = "config" ]
    then
		sh /home/***/bin/***_project_config.sh

    elif [ $2 = "redis" ]
    then
		sh /home/***/bin/***_server_setup_redis.sh

    elif [ $2 = "nginx" ]
    then
		sh /home/***/bin/***_server_setup_nginx.sh

    else
        _setup_ setup # setup as arg1
    fi
}

#
# 调用入口方法
#
_main_ $@