package cn.com.servyou;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;


@SpringBootApplication(scanBasePackages="cn.com.servyou",
        exclude={DataSourceAutoConfiguration.class, HibernateJpaAutoConfiguration.class})
public class TaskSynchronizerApplication {
    public static void main(String [] args){
        SpringApplication.run(TaskSynchronizerApplication.class, args);
    }
}
