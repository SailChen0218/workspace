#!/bin/sh

#function kill pids
killpids(){
  pids=($(ps -ef | grep $1 | awk '{print $2}'))
  for pid in ${pids[@]}; do
    echo "关闭进程: pid ${pid}"
    kill -9 ${pid}
  done
}

#function uninstall rpms
rmrpms(){
  oldRpms=($(rpm -qa|grep $1))
  for oldRpm in ${oldRpms[@]}; do
    echo "旧版$1: ${oldRpm}"
    echo "删除..."
    yum -y remove $1 ${oldRpm}
  done
}

##############################################################################################

killpids tomcat

#搜索可能的安装目录
echo "Directory list:"
paths=($(find / -maxdepth 5 -type d -name '*tomcat*'))
if [ ${#paths[@]} -lt 1 ];then
  echo "Could not find your tomcat directory!"
  exit
fi

for((i=0; i<${#paths[@]}; i++));do
  if [[ ! ${paths[i]} =~ "tmp" ]]
    then
      echo "$i. ${paths[i]}"
  fi
done
echo -n "Which one is the root directory of tomcat? "
#读取根目录
while(true)
  do
    read sn
	if [[ $sn -ge 0 && $sn -lt ${#paths[@]} ]]
	  then
        path=${paths[sn]}
        echo $path
		break
	  else
	    echo "$sn is not expected"
	fi
  done
#添加Context
rm -rf $path/conf/Catalina/localhost/manager.xml
touch $path/conf/Catalina/localhost/manager.xml
echo '<!-- ---------- ManagerContext ------------ -->' >> $path/conf/Catalina/localhost/manager.xml
IFSBAK=$IFS     #备份原来的值
IFS=#
tomcatContext="
#<Context privileged=\"true\" antiResourceLocking=\"false\"
#         docBase=\"\$\{catalina.home\}/webapps/manager\">
#    <Valve className=\"org.apache.catalina.valves.RemoteAddrValve\" allow=\"\^\.\*\$\" />
#</Context>
"
#循环写入文件
for line in $tomcatContext;
do
  sed -i "/ManagerContext/i\ $line" $path/conf/Catalina/localhost/manager.xml
done
IFS=$IFSBAK     #还原
sed -i '/ManagerContext/d' $path/conf/Catalina/localhost/manager.xml

#添加用户
#先删除
sed -i '/rolename=/d' $path/conf/tomcat-users.xml
sed -i '/username=/d' $path/conf/tomcat-users.xml
IFSBAK=$IFS     #备份原来的值
IFS=#
tomcatUser="
#   <role rolename="manager-gui" />
#     <role rolename="manager-script" />
#       <role rolename="manager-jmx" />
#         <role rolename="manager-status" />
#           <role rolename="admin-gui" />
#             <role rolename="admin-script" />
#               <user username="admin" password="admin" roles="manager-gui,manager-script,manager-jmx,manager-status,admin-gui,admin-script" />
#
#   <user username="deploy" password="deploy" roles="manager-script" />
#
"
#循环写入文件
for line in $tomcatUser;
do
  sed -i "/<\/tomcat-users>/i\ $line" $path/conf/tomcat-users.xml
done
IFS=$IFSBAK     #还原
