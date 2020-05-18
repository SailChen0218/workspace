#!/bin/bash
zookeeper_home1=/opt/zookeeper-3.4.13-1
zookeeper_home2=/opt/zookeeper-3.4.13-2
zookeeper_home3=/opt/zookeeper-3.4.13-3

case $1 in
start)
echo "启动zookeeper"
sleep 1
sudo $zookeeper_home1/bin/zkServer.sh start
sleep 3
sudo $zookeeper_home2/bin/zkServer.sh start
sleep 3
sudo $zookeeper_home3/bin/zkServer.sh start
;;

stop)
echo "关闭zookeeper"
sleep 1
sudo $zookeeper_home1/bin/zkServer.sh stop
sleep 3
sudo $zookeeper_home2/bin/zkServer.sh stop
sleep 3
sudo $zookeeper_home3/bin/zkServer.sh stop
;;

status)
echo "查看zookeeper"
sleep 1
sudo $zookeeper_home1/bin/zkServer.sh status
sleep 3
sudo $zookeeper_home2/bin/zkServer.sh status
sleep 3
sudo $zookeeper_home3/bin/zkServer.sh status
;;

esac