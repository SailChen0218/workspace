package com.ezshop.common.config.db;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;
import java.sql.SQLException;

@Configuration
@MapperScan(basePackages = "com.ezshop.dao.orderdb", sqlSessionTemplateRef = "orderdbSqlSessionTemplate")
@Slf4j
public class orderDataSourceConfig {

    @Value("${spring.datasource.orderdb.url}")
    private String dbUrl;

    @Value("${spring.datasource.orderdb.username}")
    private String username;

    @Value("${spring.datasource.orderdb.password}")
    private String password;

    @Value("${spring.datasource.orderdb.driver-class-name}")
    private String driverClassName;

    @Value("${spring.datasource.orderdb.initialSize}")
    private int initialSize;

    @Value("${spring.datasource.orderdb.minIdle}")
    private int minIdle;

    @Value("${spring.datasource.orderdb.maxActive}")
    private int maxActive;

    @Value("${spring.datasource.orderdb.maxWait}")
    private int maxWait;

    @Value("${spring.datasource.orderdb.timeBetweenEvictionRunsMillis}")
    private int timeBetweenEvictionRunsMillis;

    @Value("${spring.datasource.orderdb.minEvictableIdleTimeMillis}")
    private int minEvictableIdleTimeMillis;

    @Value("${spring.datasource.orderdb.validationQuery}")
    private String validationQuery;

    @Value("${spring.datasource.orderdb.testWhileIdle}")
    private boolean testWhileIdle;

    @Value("${spring.datasource.orderdb.testOnBorrow}")
    private boolean testOnBorrow;

    @Value("${spring.datasource.orderdb.testOnReturn}")
    private boolean testOnReturn;

    @Value("${spring.datasource.orderdb.poolPreparedStatements}")
    private boolean poolPreparedStatements;

    @Value("${spring.datasource.orderdb.maxPoolPreparedStatementPerConnectionSize}")
    private int maxPoolPreparedStatementPerConnectionSize;

    @Value("${spring.datasource.orderdb.filters}")
    private String filters;

    @Value("{spring.datasource.orderdb.connectionProperties}")
    private String connectionProperties;

    //数据源1-引用配置:spring.datasource.primary
    @Bean(name = "orderdb")
    @Qualifier("orderdb")
    @Primary
    public DataSource orderdb() {
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

    @Bean(name = "orderdbSqlSessionFactory")
    @Primary
    public SqlSessionFactory orderdbSqlSessionFactory(@Qualifier("orderdb") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().
                getResources("classpath:mybatis-mapper/orderdb/*.xml"));
        return bean.getObject();
    }

    @Bean(name = "orderdbTransactionManager")
    @Primary
    public DataSourceTransactionManager orderdbTransactionManager(@Qualifier("orderdb") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = "orderdbSqlSessionTemplate")
    @Primary
    public SqlSessionTemplate sentinelSqlSessionTemplate(@Qualifier("orderdbSqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
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
