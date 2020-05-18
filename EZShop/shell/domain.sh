#!/bin/bash
base_dir="/opt"
domain_dir="$base_dir/domains"
software_dir="$base_dir/software"
war_dir="$base_dir/wars"

echo $base_dir
echo $domain_dir
echo $software_dir
echo $war_dir

tomcat_version="8.5.33"
tomcat_tar=${software_dir}"/apache-tomcat-"${tomcat_version}".tar"
tomcat_src=$(cd `dirname $0`; pwd)"/apache-tomcat-"${tomcat_version}

echo $tomcat_src
echo $tomcat_tar

createDomain()
{
    domainName=$1
    warName=$2


    domainPath=${domain_dir}"/"${domainName}
    warSourceFile=${war_dir}"/"${warName}".war"
    warTargetFile=${domainPath}"/webapps/"$warName".war"

    echo "开始创建domain..."
    echo "domainName="${domainName}
    echo "domainPath="${domainPath}
    echo "warName="${warName}

    if [ -z "$domainName" ]
    then
        echo "请输入domain名称。"
        return 1;
    else
        if [ -d "$domainPath" ]
        then
            echo "domain已经存在，请先删除。"
            return 1
        fi
    fi

    if [ -z "$warName" ]
    then
        echo "请输入war名称，不含后缀.war。"
        return 1;
    else
        if [ ! -f "$warSourceFile" ];
        then
            echo "war文件不存在。"
            return 1;
        fi
    fi

    sleep 1
    rm -rf $tomcat_src || error_proc "删除上次解压成果出错了: $tomcat_tar"
    sleep 1
    tar -xvf $tomcat_tar || error_proc "解压出错了: $tomcat_tar"
    sleep 1
    mv $tomcat_src $domainPath || error_proc "迁移文件夹出错了：$domainPath"
    sleep 1
    cp $warSourceFile $warTargetFile || error_proc "部署war包出错了：$warSourceFile"
    sleep 1

    echo "创建domain成功。"

    return 0;
}

deleteDomain()
{
    domainName=$1
    domainPath=${domain_dir}"/"${domainName}

    echo "开始删除domain：$domainPath"

    if [ -z "$domainName" ]
    then
        echo "请输入domain的名字。"
        return 1;
    else
        if [ ! -d "$domainPath" ]
        then
            echo "domain不存在。"
            return 1;
        else
            proclist=`ps -ef | grep "domainPath"`
            for proc in $proclist
            do
                echo $proc
            done

            pidlist=`ps -ef | grep "$domainPath" | grep -v "grep" | awk '{print $2}'`
            echo $pidlist

            for pid in $pidlist
            do
                kill -9 $pid || error_proc "停止domain出错了：$domainName"
                echo "killed $pid"
            done

            rm -rf $domainPath || error_proc "删除domain出错了：$domainPath"
            echo "删除domain成功。"
            return 0;
        fi
    fi
}

error_proc()
{
    echo "$1"
    exit 1
}

while getopts c::d: opt #选项后面的冒号表示该选项需要参数
do
    case "$opt" in
        c)
            createDomain $2 $3
            shift $[ $OPTIND -1 ]
            ;;
        d)
            deleteDomain $2
            shift $[ $OPTIND -1 ]
            ;;
        *)
            echo -e "请输入以下命令及参数:\n\t -c [domainName] [warName] 创建domain。\n\t -d [domainName] 删除domain"
            exit 1
            ;;
    esac
done
