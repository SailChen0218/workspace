#!/usr/bin/env bash

consul_home=/Users/mac/git/WorkSpace/EZShop/opt/consul

${consul_home}/consul agent -config-file=${consul_home}/config-client.json -data-dir=${consul_home}/data/ -join=172.16.1.4 -bind=192.168.0.110