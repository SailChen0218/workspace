package cn.com.servyou.common.utils;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class IPUtil {
    public static String getLocalIp(){
        try{
            return InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException ex){
            return "";
        }
    }
}
