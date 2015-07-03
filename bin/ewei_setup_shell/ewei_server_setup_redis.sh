#!/bin/sh
#
# setup server setup redis
# limingwei
# 2015-04-09 16:32:27
#

REDIS_PASSWORD=info169.cn

# paths
APP_PATH=/home/***/app
DOWN_PATH=/home/***/down

# mkdirs
mkdir -p $APP_PATH
mkdir -p $DOWN_PATH

# download
cd $DOWN_PATH
if [ ! -e "$DOWN_PATH/redis-2.8.19.tar.gz" ]
then
    wget http://7u2toi.com2.z0.glb.qiniucdn.com/redis-2.8.19.tar.gz
fi

# install redis
cd $DOWN_PATH
tar zxvf redis-2.8.19.tar.gz -C $APP_PATH
mv $APP_PATH/redis-2.8.19 $APP_PATH/redis
cd $APP_PATH/redis
make
rm -rf /home/***/app/redis/redis.conf

echo "daemonize no
databases 1
requirepass $REDIS_PASSWORD
pidfile /var/run/redis.pid
port 6379
tcp-backlog 511
timeout 0
tcp-keepalive 0
loglevel notice
logfile \"\"
save 900 1
save 300 10
save 60 10000
stop-writes-on-bgsave-error yes
rdbcompression yes
rdbchecksum yes
dbfilename dump.rdb
dir ./
slave-serve-stale-data yes
slave-read-only yes
repl-diskless-sync no
repl-diskless-sync-delay 5
repl-disable-tcp-nodelay no
slave-priority 100
appendonly no
appendfilename \"appendonly.aof\"
appendfsync everysec
no-appendfsync-on-rewrite no
auto-aof-rewrite-percentage 100
auto-aof-rewrite-min-size 64mb
aof-load-truncated yes
lua-time-limit 5000
slowlog-log-slower-than 10000
slowlog-max-len 128
latency-monitor-threshold 0
notify-keyspace-events \"\"
hash-max-ziplist-entries 512
hash-max-ziplist-value 64
list-max-ziplist-entries 512
list-max-ziplist-value 64
set-max-intset-entries 512
zset-max-ziplist-entries 128
zset-max-ziplist-value 64
hll-sparse-max-bytes 3000
activerehashing yes
client-output-buffer-limit normal 0 0 0
client-output-buffer-limit slave 256mb 64mb 60
client-output-buffer-limit pubsub 32mb 8mb 60
hz 10
aof-rewrite-incremental-fsync yes" >> /home/***/app/redis/redis.conf

# redis auto run
echo "$APP_PATH/redis/src/redis-server" >> /etc/rc.d/rc.local

# redis run
$APP_PATH/redis/src/redis-server /home/***/app/redis/redis.conf