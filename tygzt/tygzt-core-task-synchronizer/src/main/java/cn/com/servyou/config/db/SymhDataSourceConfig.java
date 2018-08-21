package cn.com.servyou.config.db;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
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
@MapperScan(basePackages = "cn.com.servyou.dao.symh", sqlSessionTemplateRef = "symhSqlSessionTemplate")
public class SymhDataSourceConfig {
    // 日志
    private static final Log LOG = LogFactory.getLog(SymhDataSourceConfig.class.getName());

    @Value("${spring.datasource.symh.url}")
    private String dbUrl;

    @Value("${spring.datasource.symh.username}")
    private String username;

    @Value("${spring.datasource.symh.password}")
    private String password;

    @Value("${spring.datasource.symh.driver-class-name}")
    private String driverClassName;

    @Value("${spring.datasource.symh.initialSize}")
    private int initialSize;

    @Value("${spring.datasource.symh.minIdle}")
    private int minIdle;

    @Value("${spring.datasource.symh.maxActive}")
    private int maxActive;

    @Value("${spring.datasource.symh.maxWait}")
    private int maxWait;

    @Value("${spring.datasource.symh.timeBetweenEvictionRunsMillis}")
    private int timeBetweenEvictionRunsMillis;

    @Value("${spring.datasource.symh.minEvictableIdleTimeMillis}")
    private int minEvictableIdleTimeMillis;

    @Value("${spring.datasource.symh.validationQuery}")
    private String validationQuery;

    @Value("${spring.datasource.symh.testWhileIdle}")
    private boolean testWhileIdle;

    @Value("${spring.datasource.symh.testOnBorrow}")
    private boolean testOnBorrow;

    @Value("${spring.datasource.symh.testOnReturn}")
    private boolean testOnReturn;

    @Value("${spring.datasource.symh.poolPreparedStatements}")
    private boolean poolPreparedStatements;

    @Value("${spring.datasource.symh.maxPoolPreparedStatementPerConnectionSize}")
    private int maxPoolPreparedStatementPerConnectionSize;

    @Value("${spring.datasource.symh.filters}")
    private String filters;

    @Value("{spring.datasource.symh.connectionProperties}")
    private String connectionProperties;

    //数据源1-引用配置:spring.datasource.primary
    @Bean(name = "symhdb")
    @Qualifier("symhdb")
    @Primary
    public DataSource symhdb() {
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
            LOG.error("druid configuration initialization filter", e);
        }
        datasource.setConnectionProperties(connectionProperties);

        return datasource;
    }

    @Bean(name = "symhSqlSessionFactory")
    @Primary
    public SqlSessionFactory symhSqlSessionFactory(@Qualifier("symhdb") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().
                getResources("classpath:mybatis-mapper/symh/*.xml"));
        return bean.getObject();
    }

    @Bean(name = "symhTransactionManager")
    @Primary
    public DataSourceTransactionManager symhTransactionManager(@Qualifier("symhdb") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = "symhSqlSessionTemplate")
    @Primary
    public SqlSessionTemplate sentinelSqlSessionTemplate(@Qualifier("symhSqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
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
