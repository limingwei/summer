#!/bin/sh
#
# server setup nginx
# limingwei
# 2015-04-09 16:32:27
#

# paths
APP_PATH=/home/***/app
DOWN_PATH=/home/***/down

# mkdirs
mkdir -p $APP_PATH
mkdir -p $DOWN_PATH

# download
cd $DOWN_PATH
if [ ! -e "$DOWN_PATH/nginx-1.7.11.tar.gz" ]
then
    wget http://7u2toi.com2.z0.glb.qiniucdn.com/nginx-1.7.11.tar.gz
fi

# make install nginx
cd $DOWN_PATH
tar zxvf nginx-1.7.11.tar.gz 
cd nginx*/ 
./configure --prefix=/home/***/app/nginx --with-http_stub_status_module --with-http_ssl_module --with-http_realip_module --with-http_gzip_static_module
make
make install

# nginx auto run
echo "$APP_PATH/nginx/sbin/nginx" >> /etc/rc.d/rc.local

# nginx run
$APP_PATH/nginx/sbin/nginx