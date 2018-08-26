package cn.com.servyou.config.db;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;
import java.sql.SQLException;

@Configuration
@MapperScan(basePackages = "cn.com.servyou.dao.mydemo", sqlSessionTemplateRef = "mydemoSqlSessionTemplate")
@Slf4j
public class MydemoDataSourceConfig {

    @Value("${spring.datasource.mydemo.url}")
    private String dbUrl;

    @Value("${spring.datasource.mydemo.username}")
    private String username;

    @Value("${spring.datasource.mydemo.password}")
    private String password;

    @Value("${spring.datasource.mydemo.driver-class-name}")
    private String driverClassName;

    @Value("${spring.datasource.mydemo.initialSize}")
    private int initialSize;

    @Value("${spring.datasource.mydemo.minIdle}")
    private int minIdle;

    @Value("${spring.datasource.mydemo.maxActive}")
    private int maxActive;

    @Value("${spring.datasource.mydemo.maxWait}")
    private int maxWait;

    @Value("${spring.datasource.mydemo.timeBetweenEvictionRunsMillis}")
    private int timeBetweenEvictionRunsMillis;

    @Value("${spring.datasource.mydemo.minEvictableIdleTimeMillis}")
    private int minEvictableIdleTimeMillis;

    @Value("${spring.datasource.mydemo.validationQuery}")
    private String validationQuery;

    @Value("${spring.datasource.mydemo.testWhileIdle}")
    private boolean testWhileIdle;

    @Value("${spring.datasource.mydemo.testOnBorrow}")
    private boolean testOnBorrow;

    @Value("${spring.datasource.mydemo.testOnReturn}")
    private boolean testOnReturn;

    @Value("${spring.datasource.mydemo.poolPreparedStatements}")
    private boolean poolPreparedStatements;

    @Value("${spring.datasource.mydemo.maxPoolPreparedStatementPerConnectionSize}")
    private int maxPoolPreparedStatementPerConnectionSize;

    @Value("${spring.datasource.mydemo.filters}")
    private String filters;

    @Value("{spring.datasource.mydemo.connectionProperties}")
    private String connectionProperties;

    //数据源1-引用配置:spring.datasource.primary
    @Bean(name = "mydemodb")
    @Qualifier("mydemodb")
    @Primary
    public DataSource mydemodb() {
        DruidDataSource datasource = new DruidDataSource();

        datasource.setUrl(this.dbUrl);
        datasource.setUsername(username);
        datasource.setPassword(password);
        datasource.setDriverClassName(driverClassName);

        //configuration
        datasource.setInitialSize(initialSize);
        datasource.setMinIdle(minIdle);
        datasource.setMaxActive(maxActive);
        datasource.setMaxWait(maxWait);
        datasource.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);
        datasource.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
        datasource.setValidationQuery(validationQuery);
        datasource.setTestWhileIdle(testWhileIdle);
        datasource.setTestOnBorrow(testOnBorrow);
        datasource.setTestOnReturn(testOnReturn);
        datasource.setPoolPreparedStatements(poolPreparedStatements);
        datasource.setMaxPoolPreparedStatementPerConnectionSize(maxPoolPreparedStatementPerConnectionSize);
        try {
            datasource.setFilters(filters);
        } catch (SQLException e) {
            log.error("druid configuration initialization filter", e);
        }
        datasource.setConnectionProperties(connectionProperties);

        return datasource;
    }

    @Bean(name = "mydemoSqlSessionFactory")
    @Primary
    public SqlSessionFactory mydemoSqlSessionFactory(@Qualifier("mydemodb") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().
                getResources("classpath:mybatis-mapper/mydemo/*.xml"));
        return bean.getObject();
    }

    @Bean(name = "mydemoTransactionManager")
    @Primary
    public DataSourceTransactionManager mydemoTransactionManager(@Qualifier("mydemodb") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = "mydemoSqlSessionTemplate")
    @Primary
    public SqlSessionTemplate sentinelSqlSessionTemplate(@Qualifier("mydemoSqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }

    @Bean
    public ServletRegistrationBean getStatViewServletRegistrationBean() {
        ServletRegistrationBean bean = new ServletRegistrationBean(new StatViewServlet());
        bean.setName("DruidStatView");
        bean.addInitParameter("resetEnable", "false");
        bean.addInitParameter("loginUsername", "admin");
        bean.addInitParameter("loginPassword", "admin");
        bean.addInitParameter("logSlowSql", "true");
        bean.addUrlMappings("/druid/*");
        return bean;
    }

    @Bean
    public FilterRegistrationBean filterRegistrationBean() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        filterRegistrationBean.setFilter(new WebStatFilter());
        filterRegistrationBean.addUrlPatterns("/*");
        filterRegistrationBean.addInitParameter("exclusions", "*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*");
        filterRegistrationBean.addInitParameter("profileEnable", "true");
        return filterRegistrationBean;
    }
}
