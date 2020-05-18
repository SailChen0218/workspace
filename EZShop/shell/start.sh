#!/bin/bash
error_proc()
{
    echo "$1"
    exit 1
}

./opt/consul/consul agent -config-dir config.json -node=task -advertise=172.18.0.14 -join=172.18.0.11 || error_proc "consul启动出错了。"

