#!/bin/bash
case $1 in
start)
docker run -d --restart always -p 8300:8300 -p 8301:8301 -p 8301:8301/udp -p 8302:8302/udp -p 8302:8302 -p 8400:8400 -p 8500:8500 -p 53:53/udp -h server1 5182e96772bf /opt/consul/consul agent -config-dir /opt/consul/config-server.json
;;

stop)
;;


esac