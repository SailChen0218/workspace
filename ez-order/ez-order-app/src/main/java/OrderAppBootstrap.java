import com.ezddd.common.bean.EnableEzdddFramework;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;

@EnableEzdddFramework
@SpringBootApplication(scanBasePackages = {"com.ezddd", "com.ezshop"},
        exclude = {DataSourceAutoConfiguration.class, HibernateJpaAutoConfiguration.class})
public class OrderAppBootstrap {
    public static void main(String[] args) {
        SpringApplication.run(OrderAppBootstrap.class, args);
    }
}
