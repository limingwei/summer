#!/bin/sh
#
# setup project config files
# limingwei
# 2015-04-09 16:32:27
#

# paths
SRC_PATH=/home/***/source-code
APP_PATH=/home/***/app

# jetty-***-push
cd $APP_PATH/jetty-***-push/webapps
rm -rf ***-push-jetty-config.xml
cp $SRC_PATH/***-support/bin/server-conf/***-push-jetty-config.xml $APP_PATH/jetty-***-push/webapps

# tomcat-***-admin
cd $APP_PATH/tomcat-***-admin/conf
rm -rf server.xml
cp $SRC_PATH/***-support/bin/server-conf/***-admin-tomcat-server.xml $APP_PATH/tomcat-***-admin/conf
mv $APP_PATH/tomcat-***-admin/conf/***-admin-tomcat-server.xml $APP_PATH/tomcat-***-admin/conf/server.xml

# tomcat-***-open
cd $APP_PATH/tomcat-***-open/conf
rm -rf server.xml
cp $SRC_PATH/***-support/bin/server-conf/***-open-tomcat-server.xml $APP_PATH/tomcat-***-open/conf
mv $APP_PATH/tomcat-***-open/conf/***-open-tomcat-server.xml $APP_PATH/tomcat-***-open/conf/server.xml

# tomcat-***-web
cd $APP_PATH/tomcat-***-web/conf
rm -rf server.xml
cp $SRC_PATH/***-support/bin/server-conf/***-web-tomcat-server.xml $APP_PATH/tomcat-***-web/conf
mv $APP_PATH/tomcat-***-web/conf/***-web-tomcat-server.xml $APP_PATH/tomcat-***-web/conf/server.xml

# project config files
cd $SRC_PATH/***-support/
mvn test -Dtest=com.***.setup.***SetupProjectConfig