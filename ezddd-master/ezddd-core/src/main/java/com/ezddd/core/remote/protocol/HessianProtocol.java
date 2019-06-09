package com.ezddd.core.remote.protocol;

import com.ezddd.core.remote.receiver.IRemoteCallReceiver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.remoting.caucho.HessianServiceExporter;

@Configuration
public class HessianProtocol implements Protocol {
    private DefaultListableBeanFactory beanFactory;

    @Autowired
    IRemoteCallReceiver remoteCallReceiver;

    @Bean("/hessian.do")
    public HessianServiceExporter exportDomainService() {
        HessianServiceExporter exporter = new HessianServiceExporter();
        exporter.setService(remoteCallReceiver);
        exporter.setServiceInterface(IRemoteCallReceiver.class);
        return exporter;
    }
}