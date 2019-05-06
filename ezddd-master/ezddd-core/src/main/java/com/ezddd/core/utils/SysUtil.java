package com.ezddd.core.utils;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

public class SysUtil {
    /**
     * 将OBJECT转换成BASE64编码的STRING
     *
     * @param obj
     * @return
     * @throws IOException
     * @throws NullPointerException
     */
    public static String objectToString(Object obj) throws IOException, NullPointerException {
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        ObjectOutputStream out = null;
        String strout = null;
        try {
            out = new ObjectOutputStream(outStream);
            if (obj == null) {
                throw new NullPointerException("对象为空!");
            }
            out.writeObject(obj);
            out.flush();
            strout = Base64.encodeToString(outStream.toByteArray());
        } finally {
            out.close();
            outStream.close(); // 释放outStream
        }
        return strout;
    }
}
