#!/bin/bash
base_dir="/opt"
software_dir="$base_dir/software"

jdk_tar="jdk-8u181-linux-x64.tar"
jdk_src=$(cd `dirname $0`; pwd)"/jdk-8u181-linux-x64"
jdk_targetPath="$base_dir/java/jdk1.8.0_181"

echo "开始安装jdk..."
echo $base_dir
echo $software_dir
echo $jdk_tar
echo $jdk_src

if [ -d "$jdk_src" ]
then
    echo "jdk目录已经存在，请确认jdk是否已经安装。"
    return 1
fi

error_proc()
{
    echo "$1"
    exit 1
}

tar -xvf $jdk_tar || error_proc "解压出错了: $jdk_tar"
sleep 1
mv $jdk_src $jdk_targetPath || error_proc "迁移文件夹出错了：$jdk_src"
sleep 1

echo "JAVA_HOME=$jdk_targetPath" >> /etc/profile
echo "JRE_HOME=\$JAVA_HOME/jre" >> /etc/profile
echo "CLASSPATH=.:\$JAVA_HOME/lib:\$JRE_HOME/lib" >> /etc/profile
echo "PATH=\$JAVA_HOME/bin:$PATH" >> /etc/profile

source /etc/profile
java -version || error_proc "JDK安装出错了：$jdk_tar"
echo "jdk安装完成。"