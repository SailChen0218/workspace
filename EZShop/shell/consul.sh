#!/bin/bash
if [ $# < 2 ]
then
    echo "请输入consul的[-advertise]、[-join]参数。"
else
    consul agent -config-dir config.json -node=task -advertise=172.18.0.14 -join=172.18.0.11
fi