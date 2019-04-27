package com.ezddd.app.config;

import com.ezddd.app.dispatcher.impl.DispatcherServlet;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//@Configuration
public class ServletConfig {

//    @Bean
    public ServletRegistrationBean servletRegistrationBean() {
        return new ServletRegistrationBean(new DispatcherServlet(), "/test");
    }

}
