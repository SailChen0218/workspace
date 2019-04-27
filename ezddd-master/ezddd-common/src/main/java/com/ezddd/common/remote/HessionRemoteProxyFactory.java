package com.ezddd.common.remote;

import com.caucho.hessian.client.HessianProxyFactory;
import com.ezddd.common.bean.EzBeanFactoryPostProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.MalformedURLException;

public class HessionRemoteProxyFactory implements RemoteProxyFactory {
    private static final Logger log = LoggerFactory.getLogger(EzBeanFactoryPostProcessor.class);
    private static HessianProxyFactory hessianProxyFactory;
    private static String HESSION_URI = "http://localhost:8081/order/domain/hessian.do";

    static {
        hessianProxyFactory = new HessianProxyFactory();
        hessianProxyFactory.setOverloadEnabled(true);
    }

    @Override
    public <T> T create(Class<T> clazz) {
        try {
            T bean = (T) hessianProxyFactory.create(clazz, HESSION_URI);
            return bean;
        } catch (MalformedURLException e) {
            log.error("MalformedURLException occurred", e);
            return null;
        }
    }
}
