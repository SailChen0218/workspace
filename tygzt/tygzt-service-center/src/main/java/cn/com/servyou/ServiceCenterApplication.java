package cn.com.servyou;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication(scanBasePackages="cn.com.servyou",
        exclude={DataSourceAutoConfiguration.class, HibernateJpaAutoConfiguration.class})
public class ServiceCenterApplication {
    public static void main(String [] args){
        SpringApplication.run(ServiceCenterApplication.class, args);
    }
}
