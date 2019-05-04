package com.ezddd.core.remote.provider;

import com.ezddd.core.annotation.EzDomainService;
import com.ezddd.core.remote.RpcType;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.remoting.caucho.HessianServiceExporter;

@Configuration
public class HessionRpcProvider implements RpcProvider, BeanFactoryPostProcessor {
    private DefaultListableBeanFactory beanFactory;

    @Bean("/hessian.do")
    public HessianServiceExporter exportDomainService()
    {
        HessianServiceExporter exporter = new HessianServiceExporter();
        String[] beanNames = beanFactory.getBeanNamesForAnnotation(EzDomainService.class);
        if (beanNames != null) {
            for (String beanName: beanNames) {
                Object bean = beanFactory.getBean(beanName);
                EzDomainService ezDomainService = bean.getClass().getAnnotation(EzDomainService.class);
                if (ezDomainService.rpcType() == RpcType.HESSIAN) {
                    exporter.setService(bean);
                    exporter.setServiceInterface(ezDomainService.interfaceType());
                }
            }
        }
        return exporter;
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        this.beanFactory = (DefaultListableBeanFactory)beanFactory;
    }
}