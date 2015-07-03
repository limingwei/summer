#!/bin/sh
#
# setup server setup
# limingwei
# 2015-04-09 16:32:27
#

# yum install
yum -y install gcc gcc-c++ pcre-devel openssl openssl-devel make subversion tcl

# paths
APP_PATH=/home/***/app
DOWN_PATH=/home/***/down
CONF_PATH=/home/***/conf

# mkdirs
mkdir -p $APP_PATH
mkdir -p $DOWN_PATH
mkdir -p $CONF_PATH

# unpack jdk
cd $DOWN_PATH
if [ ! -e "$DOWN_PATH/jdk7.tar.gz" ]
then
    wget http://7u2toi.com2.z0.glb.qiniucdn.com/jdk7.tar.gz
fi
rm -rf $APP_PATH/jdk
tar zxvf jdk7.tar.gz -C $APP_PATH/
mv $APP_PATH/jdk1.7.0_75 $APP_PATH/jdk

# unpack maven
cd $DOWN_PATH
if [ ! -e "$DOWN_PATH/maven-3.3.1.tar.gz" ]
then
    wget http://7u2toi.com2.z0.glb.qiniucdn.com/maven-3.3.1.tar.gz
fi
rm -rf $APP_PATH/maven
tar zxvf maven-3.3.1.tar.gz -C $APP_PATH
mv $APP_PATH/apache-maven-3.3.1 $APP_PATH/maven
# maven-config-file
cd $APP_PATH/maven/conf/
rm -rf settings.xml
echo "<?xml version=\"1.0\" encoding=\"UTF-8\"?>
<settings xmlns=\"http://maven.apache.org/SETTINGS/1.0.0\" 
          xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" 
          xsi:schemaLocation=\"http://maven.apache.org/SETTINGS/1.0.0 http://maven.apache.org/xsd/settings-1.0.0.xsd\">
  <localRepository>/home/***/maven-local-repository</localRepository>
</settings>" >> settings.xml

# install yasm
cd $DOWN_PATH 
if [ ! -e "$DOWN_PATH/yasm-1.3.0.tar.gz" ]
then
    wget http://7u2toi.com2.z0.glb.qiniucdn.com/yasm-1.3.0.tar.gz
fi
tar zxvf yasm-1.3.0.tar.gz
cd yasm*/ 
./configure 
make 
make install

# install ffmpeg
cd $DOWN_PATH 
if [ ! -e "$DOWN_PATH/ffmpeg-2.6.1.tar.bz2" ]
then
    wget http://7u2toi.com2.z0.glb.qiniucdn.com/ffmpeg-2.6.1.tar.bz2
fi
tar jxvf ffmpeg-2.6.1.tar.bz2 
cd ffmpeg*/ 
./configure --enable-shared --prefix=/home/***/app/ffmpeg
make 
make install
# ffmpeg libs
echo "/home/***/app/ffmpeg/lib" >> /etc/ld.so.conf && ldconfig

# *** profile
if [ ! -e "/home/***/conf/etc_profile_backup_for_***_server_setup" ]
then
    cp /etc/profile /home/***/conf/etc_profile_backup_for_***_server_setup
else
    rm -rf /etc/profile
    cp /home/***/conf/etc_profile_backup_for_***_server_setup /etc/profile
fi

echo "#
# *** server setup
#
# set java env
export JAVA_HOME=/home/***/app/jdk
export CLASSPATH=.:\$JAVA_HOME/lib/dt.jar:\$JAVA_HOME/lib/tools.jar
export PATH=\$JAVA_HOME/bin:\$PATH
#
# set maven env
export MAVEN_HOME=/home/***/app/maven
export PATH=\$MAVEN_HOME/bin:\$PATH
#
# set log file root
export logFileRoot=/home/***" >> /etc/profile && source /etc/profile