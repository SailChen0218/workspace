package com;

import com.ezddd.core.spring.EnableEzdddDomain;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;

@EnableEzdddDomain(basePackages = "com.ezshop")
@SpringBootApplication(scanBasePackages = {"com.ezshop"},
        exclude = {DataSourceAutoConfiguration.class, HibernateJpaAutoConfiguration.class})
public class OrderDomainBootstrap {
    public static void main(String [] args){
        SpringApplication.run(OrderDomainBootstrap.class, args);
    }
}
