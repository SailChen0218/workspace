package cn.com.servyou.eureka;

//import cn.com.servyou.common.utils.IPUtil;
//import com.netflix.appinfo.ApplicationInfoManager;
//import com.netflix.appinfo.EurekaInstanceConfig;
//import com.netflix.appinfo.InstanceInfo;
//import com.netflix.appinfo.MyDataCenterInstanceConfig;
//import com.netflix.appinfo.providers.EurekaConfigBasedInstanceInfoProvider;
//import com.netflix.config.ConfigurationManager;
//import com.netflix.discovery.DefaultEurekaClientConfig;
//import com.netflix.discovery.DiscoveryClient;
//
//import javax.xml.ws.Provider;
//import java.io.InputStream;
//import java.util.Properties;

public class eurekaRegister {
//    private void initEurekaClient() throws Exception{
//        Properties properties = new Properties();
//        InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("eureka.properties");
//        properties.load(inputStream);
//
//        properties.setProperty("eureka.ipAddr", IPUtil.getLocalIp());
//        String instanceId = properties.getProperty("eureka.ipAddr") + ":" + properties.getProperty("eureka.ipAddr") + "/" + properties.getProperty("eureka.name");
//        properties.setProperty("eureka.instanceId", instanceId);
//
//        ConfigurationManager.loadProperties(properties);
//        MyDataCenterInstanceConfig instanceConfig = new MyDataCenterInstanceConfig();
//        InstanceInfo instanceInfo = new EurekaConfigBasedInstanceInfoProvider(instanceConfig);
//        ApplicationInfoManager applicationInfoManager = new ApplicationInfoManager(instanceConfig, instanceInfo);
//        DefaultEurekaClientConfig clientConfig = new DefaultEurekaClientConfig();
//        DiscoveryClient eurekaClient = new DiscoveryClient(applicationInfoManager, clientConfig);
//    }
//
//    private void waitForRegistrationWithEureka() {
//
//        applicationInfoManager.setInstanceStatus(InstanceInfo.InstanceStatus.STARTING);
//        try {
//            Thread.sleep(2000);
//        } catch (InterruptedException e) {
//        }
//        applicationInfoManager.setInstanceStatus(InstanceInfo.InstanceStatus.UP);
//
//        long startTime = System.currentTimeMillis();
//        //开启一个线程验证注册结果
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                while (true) {
//                    if (System.currentTimeMillis() - startTime > VERIFY_WAIT_MILLIS) {
//                        log.warn(" >>>> service registration status not verify,please check it!!!!");
//                        return;
//                    }
//                    try {
//                        List<InstanceInfo> serverInfos = eurekaClient.getInstancesByVipAddress(vipAddress, false);
//                        for (InstanceInfo nextServerInfo : serverInfos) {
//                            if (nextServerInfo.getIPAddr().equals(IpUtils.LOCAL_BACK_IP)
//                                    || nextServerInfo.getIPAddr().equals(IpUtils.getLocalIpAddr())) {
//                                String instanceInfoJson = JsonUtils.getMapper().writerWithDefaultPrettyPrinter()
//                                        .writeValueAsString(nextServerInfo);
//                                log.info("verifying service registration with eureka finished,instance:\n{}",
//                                        instanceInfoJson);
//                                return;
//                            }
//                        }
//                    } catch (Throwable e) {
//                    }
//                    try {
//                        Thread.sleep(5000);
//                    } catch (Exception e1) {
//                    }
//                    log.info("Waiting 5s... verifying service registration with eureka ...");
//                }
//            }
//        }).start();
//    }
}
