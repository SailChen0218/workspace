#!/bin/bash
kafka_home=/opt/kafka_2.11-2.0.0
STARTUP=sudo $kafka_home/bin/kafka-server-start.sh $kafka_home/config/server.properties &
SHUTDOWN=sudo $kafka_home/bin/kafka-server-stop.sh

case $1 in
start)
echo "启动$kafka_home"
$STARTUP
tail -f $kafka_home/logs/server.log
;;

stop)
echo "关闭$kafka_home"
$SHUTDOWN
pidlist=`ps -ef |grep kafka_2.11-2.0.0 |grep -v "grep"|awk '{print $2}'`
kill -9 $pidlist

#删除日志文件，如果你不先删除可以不要下面一行
#rm  $kafka_home/logs/* -rf
#echo "" > $kafka_home/logs/server.log
#删除tomcat的临时目录
#rm  $kafka_home/work/* -rf
;;

restart)
echo "关闭$kafka_home"
$SHUTDOWN
pidlist=`ps -ef |grep kafka_2.11-2.0.0 |grep -v "grep"|awk '{print $2}'`
kill -9 $pidlist

#删除日志文件，如果你不先删除可以不要下面一行
#rm  $kafka_home/logs/* -rf
#删除tomcat的临时目录
#rm  $kafka_home/work/* -rf

sleep 3
echo "启动$kafka_home"
$STARTUP
#看启动日志
#tail -f $kafka_home/logs/catalina.out
;;

view)
tail -f $kafka_home/logs/server.log
;;

esac