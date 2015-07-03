#!/bin/sh
#
# setup project setup
# limingwei
# 2015-04-09 16:32:27
#


SVN_URL="svn://121.41.62.44/***-v5-source-code-root/"
# SVN_URL="http://192.168.138.202:8082/svn/code/helpdesk/"

# paths
BIN_PATH=/home/***/bin
APP_PATH=/home/***/app
DOWN_PATH=/home/***/down
CONF_PATH=/home/***/conf
SRC_PATH=/home/***/source-code

#
# 入口函数
#
function _main_()
{
	if [ $# -eq 0 ]
	then
		_***_project_setup_code_
		_***_project_setup_server_
		_***_project_setup_maven_

	elif [ $1 = "code" ]
	then
		_***_project_setup_code_

	elif [ $1 = "server" ]
	then
		_***_project_setup_server_

	elif [ $1 = "maven" ]
	then
		_***_project_setup_maven_

	else
		echo "code|server|maven"
    fi
}

function _***_project_setup_code_()
{
	rm -rf $SRC_PATH
	mkdir -p $SRC_PATH
	cd $SRC_PATH
	svn checkout ${SVN_URL}***-support
	svn checkout ${SVN_URL}***-api
	svn checkout ${SVN_URL}***-admin-api
	svn checkout ${SVN_URL}***-admin-sdk
	svn checkout ${SVN_URL}***-admin-service
	svn checkout ${SVN_URL}***-notify
	svn checkout ${SVN_URL}***-account-service
	svn checkout ${SVN_URL}***-ticket-service
	svn checkout ${SVN_URL}***-helpcenter-service
	svn checkout ${SVN_URL}***-account-logic
	svn checkout ${SVN_URL}***-ticket-logic
	svn checkout ${SVN_URL}***-helpcenter-logic
	svn checkout ${SVN_URL}***-mail
	svn checkout ${SVN_URL}***-push
	svn checkout ${SVN_URL}***-admin
	svn checkout ${SVN_URL}***-open
	svn checkout ${SVN_URL}***-web

	mkdir -p $SRC_PATH/***-support/bin/shell-online/project-status/
	echo 'no value' >> $SRC_PATH/***-support/bin/shell-online/project-status/nginx_route_status

	# init ***-shell
	mkdir -p $BIN_PATH
	rm -rf $BIN_PATH/***_project
	echo '#!/bin/sh
	sh /home/***/source-code/***-support/bin/shell-online/***.sh $@' >> $BIN_PATH/***_project && chmod 777 $BIN_PATH/***_project
}

function _***_project_setup_server_()
{
	cd $DOWN_PATH
	if [ ! -e "$DOWN_PATH/tomcat-7.0.59.tar.gz" ];then
		wget http://7u2toi.com2.z0.glb.qiniucdn.com/tomcat-7.0.59.tar.gz
	fi
	if [ ! -e "$DOWN_PATH/jetty-9.2.10.tar.gz" ];then
		wget http://7u2toi.com2.z0.glb.qiniucdn.com/jetty-9.2.10.tar.gz
	fi

	# jetty-***-push
	rm -rf $APP_PATH/jetty-***-push
	cd $DOWN_PATH
	tar zxvf jetty-9.2.10.tar.gz -C $APP_PATH
	mv $APP_PATH/jetty-distribution-9.2.10.v20150310/ $APP_PATH/jetty-***-push

	# tomcat-***-admin
	rm -rf $APP_PATH/tomcat-***-admin
	cd $DOWN_PATH
	tar zxvf tomcat-7.0.59.tar.gz -C $APP_PATH
	mv $APP_PATH/apache-tomcat-7.0.59/ $APP_PATH/tomcat-***-admin
	rm -rf $APP_PATH/tomcat-***-admin/webapps

	# tomcat-***-open
	rm -rf $APP_PATH/tomcat-***-open
	cd $DOWN_PATH
	tar zxvf tomcat-7.0.59.tar.gz -C $APP_PATH
	mv $APP_PATH/apache-tomcat-7.0.59/ $APP_PATH/tomcat-***-open
	rm -rf $APP_PATH/tomcat-***-open/webapps

	# tomcat-***-web
	rm -rf $APP_PATH/tomcat-***-web
	cd $DOWN_PATH
	tar zxvf tomcat-7.0.59.tar.gz -C $APP_PATH
	mv $APP_PATH/apache-tomcat-7.0.59/ $APP_PATH/tomcat-***-web
	rm -rf $APP_PATH/tomcat-***-web/webapps
}

function _***_project_setup_maven_()
{
	# maven-jar-files
	cd $DOWN_PATH
	if [ ! -e "$DOWN_PATH/maven-local-repository.tar.gz" ];then
		wget http://7u2toi.com2.z0.glb.qiniucdn.com/maven-local-repository.tar.gz
	fi
	tar zxvf maven-local-repository.tar.gz -C /
}

#
# 调用入口方法
#
_main_ $@