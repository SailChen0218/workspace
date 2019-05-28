package com;

import com.ezddd.core.spring.EnableEzdddDomain;
import com.ezddd.core.spring.bootstrap.AbstractDomainBootstrap;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;

@EnableEzdddDomain(basePackages = "com.ezshop")
@SpringBootApplication(scanBasePackages = {"com.ezshop"}, exclude = {
        DataSourceAutoConfiguration.class, HibernateJpaAutoConfiguration.class,
        MongoAutoConfiguration.class, MongoDataAutoConfiguration.class})
public class OrderDomainBootstrap {
    public static void main(String[] args) {
        SpringApplication.run(OrderDomainBootstrap.class, args);
    }
}
