package com.ezddd.domain.provider;

import com.ezddd.common.annotation.EzDomainService;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.remoting.caucho.HessianServiceExporter;

/**
 * <用于配置发布hessian接口>
 * <这里只是做了最简单的配置，还可以设置超时时间，密码这些安全参数>
 * @author wzh
 * @version 2018-11-18 16:55
 * @see [相关类/方法] (可选)
 **/
@Configuration //标记为spring 配置类
public class HessionProvider implements BeanFactoryPostProcessor {

    private DefaultListableBeanFactory beanFactory;
    /**
     * 1. HessianServiceExporter是由Spring.web框架提供的Hessian工具类，能够将bean转化为Hessian服务
     * 2. @Bean(name = "/helloHessian.do")加斜杠方式会被spring暴露服务路径,发布服务。
     * @return
     */
    @Bean("/hessian.do")
    public HessianServiceExporter exportHelloHessian()
    {
        HessianServiceExporter exporter = new HessianServiceExporter();
        String[] beanNames = beanFactory.getBeanNamesForAnnotation(EzDomainService.class);
        if (beanNames != null) {
            for (String beanName: beanNames) {
                Object bean = beanFactory.getBean(beanName);
                Class<?>[] interfaces = bean.getClass().getInterfaces();
                if (interfaces != null && interfaces.length != 0) {
                    exporter.setService(bean);
                    exporter.setServiceInterface(interfaces[0]);
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