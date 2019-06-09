//package com.ezddd.core.spring.shutdown;
//
//import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
//import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class EzWebConfig {
//
//    @Bean
//    public EzTomcatShutdown tomcatShutdown() {
//        return new EzTomcatShutdown();
//    }
//
//    @Bean
//    public ConfigurableServletWebServerFactory webServerFactory(EzTomcatShutdown tomcatShutdown) {
//        TomcatServletWebServerFactory factory = new TomcatServletWebServerFactory();
//        factory.addConnectorCustomizers(tomcatShutdown);
//        return factory;
//    }
//
//}
