#!/bin/sh
#
# server setup nginx
# limingwei
# 2015-04-09 16:32:27
#
# 1. 下载脚本压缩文件
# 2. 解压缩
# 3. chmod 777
# 4. 删除刚下载的
#

echo "yum install unzip zip"
yum -y install unzip zip 

echo "mkdirs"
mkdir -p /home/***/bin/
mkdir -p /home/***/conf/
mkdir -p /home/***/down/

echo "wget ***_setup.zip and unzip"
cd /home/***/bin/
if [ ! -e "/home/***/down/***_setup.zip" ];then
	cd /home/***/down/
	wget http://7u2toi.com2.z0.glb.qiniucdn.com/***_setup.zip
fi
cp /home/***/down/***_setup.zip /home/***/bin/***_setup.zip

cd /home/***/bin/
unzip -o ***_setup.zip

cp ***.sh ***
chmod 777 ***

rm -rf ***_setup.zip

# update *** profile
echo "update profile"
if [ -e "/home/***/conf/etc_profile_backup_for_***_setup" ];then
    rm -rf /etc/profile
    cp /home/***/conf/etc_profile_backup_for_***_setup /etc/profile
else
    cp /etc/profile /home/***/conf/etc_profile_backup_for_***_setup
fi

echo "#
# *** setup
#
# set *** env
export PATH=/home/***/bin:\$PATH
# " >> /etc/profile && source /etc/profile

echo "done"
echo "use command ***"