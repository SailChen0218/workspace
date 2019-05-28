package com.ezddd.extension.datasource.mongodb;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;

import java.util.ArrayList;
import java.util.List;

@Configuration
@Validated
@PropertySource(value = "classpath:mongodb.properties")
public class MongoConfig {
    @Value("${mongo.database}")
    private String database;

    @Value("${mongo.address}")
    private List<String> address;

    @Value("${mongo.replicaSet:}")
    private String replicaSet;

    @Value("${mongo.username}")
    private String username;

    @Value("${mongo.password}")
    private String password;

    @Value("${mongo.options.min-connections-per-host:0}")
    private Integer minConnectionsPerHost;

    @Value("${mongo.options.max-connections-per-host:100}")
    private Integer maxConnectionsPerHost;

    @Value("${mongo.options.threads-allowed-to-block-for-connection-multiplier:5}")
    private Integer threadsAllowedToBlockForConnectionMultiplier;

    @Value("${mongo.options.server-selection-timeout:30000}")
    private Integer serverSelectionTimeou;

    @Value("${mongo.options.max-wait-time:120000}")
    private Integer maxWaitTime;

    @Value("${mongo.options.max-connection-idel-time:0}")
    private Integer maxConnectionIdleTime;

    @Value("${mongo.options.max-connection-life-time:0}")
    private Integer maxConnectionLifeTime;

    @Value("${mongo.options.connect-timeout:10000}")
    private Integer connectTimeout;

    @Value("${mongo.options.socket-timeout:0}")
    private Integer socketTimeout;

    @Value("${mongo.options.socket-keep-alive:false}")
    private Boolean socketKeepAlive;

    @Value("${mongo.options.ssl-enabled:false}")
    private Boolean sslEnabled;

    @Value("${mongo.options.ssl-invalid-host-name-allowed:false}")
    private Boolean sslInvalidHostNameAllowed;

    @Value("${mongo.options.always-use-m-beans:false}")
    private Boolean alwaysUseMBeans;

    @Value("${mongo.options.heartbeat-socket-timeout:10000}")
    private Integer heartbeatFrequency;

    @Value("${mongo.options.heartbeat-connect-timeout:500}")
    private Integer minHeartbeatFrequency;

    @Value("${mongo.options.min-heartbeat-frequency:20000}")
    private Integer heartbeatConnectTimeout;

    @Value("${mongo.options.heartbeat-frequency:20000}")
    private Integer heartbeatSocketTimeout;

    @Value("${mongo.options.local-threshold:15}")
    private Integer localThreshold;

    @Value("${mongo.options.authentication-database:admin}")
    private String authenticationDatabase;

    //覆盖默认的MongoDbFacotry
    @Bean
    public MongoDbFactory mongoDbFactory() {
        //客户端配置（连接数，副本集群验证）
        MongoClientOptions.Builder builder = new MongoClientOptions.Builder();
        builder.connectionsPerHost(this.maxConnectionsPerHost);
        builder.minConnectionsPerHost(this.minConnectionsPerHost);
        if (StringUtils.isEmpty(this.replicaSet)) {
            builder.requiredReplicaSetName(this.replicaSet);
        }

        builder.threadsAllowedToBlockForConnectionMultiplier(
                this.threadsAllowedToBlockForConnectionMultiplier);
        builder.serverSelectionTimeout(this.serverSelectionTimeou);
        builder.maxWaitTime(this.maxWaitTime);
        builder.maxConnectionIdleTime(this.maxConnectionIdleTime);
        builder.maxConnectionLifeTime(this.maxConnectionLifeTime);
        builder.connectTimeout(this.connectTimeout);
        builder.socketTimeout(this.socketTimeout);
//        builder.socketKeepAlive(properties.getSocketKeepAlive());
        builder.sslEnabled(this.sslEnabled);
        builder.sslInvalidHostNameAllowed(this.sslInvalidHostNameAllowed);
        builder.alwaysUseMBeans(this.alwaysUseMBeans);
        builder.heartbeatFrequency(this.heartbeatFrequency);
        builder.minHeartbeatFrequency(this.minHeartbeatFrequency);
        builder.heartbeatConnectTimeout(this.heartbeatConnectTimeout);
        builder.heartbeatSocketTimeout(this.heartbeatSocketTimeout);
        builder.localThreshold(this.localThreshold);
        MongoClientOptions mongoClientOptions = builder.build();

        // MongoDB地址列表
        List<ServerAddress> serverAddresses = new ArrayList<>();
        for (String address : this.address) {
            String[] hostAndPort = address.split(":");
            String host = hostAndPort[0];
            Integer port = Integer.parseInt(hostAndPort[1]);
            ServerAddress serverAddress = new ServerAddress(host, port);
            serverAddresses.add(serverAddress);
        }

        // 连接认证
        MongoCredential mongoCredential = MongoCredential.createScramSha1Credential(this.username,
                this.authenticationDatabase != null ? this.authenticationDatabase : this.database,
                this.password.toCharArray());

        //创建客户端和Factory
        MongoClient mongoClient = new MongoClient(serverAddresses, mongoCredential, mongoClientOptions);
        MongoDbFactory mongoDbFactory = new SimpleMongoDbFactory(mongoClient, this.database);
        return mongoDbFactory;
    }

    @Bean
    @Autowired
    public MongoTemplate mongoTemplate(MongoDbFactory mongoDbFactory) {
        return new MongoTemplate(mongoDbFactory);
    }
}
