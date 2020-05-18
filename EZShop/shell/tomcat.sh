#!/bin/bash

if [ $# < 2 ]
then
    echo "请输入执行参数: [cmdType] [domainName]，例如：./tomcat.sh start ezshop-core-user"
    return 1;
fi

tomcat_home="/opt/domains/$2"
SHUTDOWN="$tomcat_home/bin/shutdown.sh"
STARTTOMCAT="$tomcat_home/bin/startup.sh"

case $1 in
    start)
    echo "启动$tomcat_home"
    $STARTTOMCAT
    #tail -f $tomcat_home/logs/catalina.out
    ;;

    stop)
    echo "关闭$tomcat_home"
    $SHUTDOWN
    pidlist=`ps -ef |grep "$tomcat_home" |grep -v "grep"|awk '{print $2}'`
    kill -9 $pidlist

    #删除日志文件，如果你不先删除可以不要下面一行
    rm  $tomcat_home/logs/* -rf
    #删除tomcat的临时目录
    rm  $tomcat_home/work/* -rf
    ;;

    restart)
    echo "关闭$tomcat_home"
    $SHUTDOWN
    pidlist=`ps -ef |grep "$tomcat_home" |grep -v "grep"|awk '{print $2}'`
    kill -9 $pidlist

    #删除日志文件，如果你不先删除可以不要下面一行
    rm  $tomcat_home/logs/* -rf
    #删除tomcat的临时目录
    rm  $tomcat_home/work/* -rf

    sleep 5
    echo "启动$tomcat_home"
    $STARTTOMCAT
    #看启动日志
    #tail -f $tomcat_home/logs/catalina.out
    ;;

    view)
    #tail -f $tomcat_home/logs/catalina.out
    ;;
esac

