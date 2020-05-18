防火墙的相关操作：
启动：systemctl start firewalld
查看状态：systemctl status firewalld 
停止： systemctl disable firewalld
禁用： systemctl stop firewalld

查看所有打开的端口： firewall-cmd --zone=public --list-ports
开启端口：
firewall-cmd --zone=public --add-port=80/tcp --permanent    （--permanent永久生效，没有此参数重启后失效）
重新载入
firewall-cmd --reload
查看
firewall-cmd --zone= public --query-port=80/tcp
删除
firewall-cmd --zone= public --remove-port=80/tcp --permanent
显示状态： firewall-cmd --state