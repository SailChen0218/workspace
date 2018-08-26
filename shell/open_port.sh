#!/bin/bash

firewall-cmd --zone=public --add-port=8300/tcp --permanent
firewall-cmd --zone=public --add-port=8301/tcp --permanent
firewall-cmd --zone=public --add-port=8302/tcp --permanent
firewall-cmd --zone=public --add-port=8400/tcp --permanent
firewall-cmd --zone=public --add-port=8500/tcp --permanent
firewall-cmd --zone=public --add-port=53/tcp --permanent
firewall-cmd --reload
