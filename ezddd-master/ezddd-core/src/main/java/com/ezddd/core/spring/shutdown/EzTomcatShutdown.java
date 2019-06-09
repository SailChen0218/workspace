//package com.ezddd.core.spring.shutdown;
//
//
//import com.ezddd.core.mq.Consumer;
//import com.ezddd.core.mq.Producer;
//import org.apache.catalina.connector.Connector;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.BeansException;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.boot.web.embedded.tomcat.TomcatConnectorCustomizer;
//import org.springframework.context.ApplicationContext;
//import org.springframework.context.ApplicationContextAware;
//import org.springframework.context.ApplicationListener;
//import org.springframework.context.event.ContextClosedEvent;
//
//import java.util.Map;
//import java.util.concurrent.*;
//
//public class EzTomcatShutdown implements ApplicationListener<ContextClosedEvent>,
//        TomcatConnectorCustomizer, ApplicationContextAware {
//    private static final Logger log = LoggerFactory.getLogger(EzTomcatShutdown.class);
//
//    private volatile Connector connector;
//
//    private ApplicationContext applicationContext;
//
//    @Value("${app.shutdownTimeout:}")
//    private int shutdownTimeout;
//
//    @Override
//    public void onApplicationEvent(ContextClosedEvent event) {
//        this.connector.pause();
//
//        prepareShutdown();
//
//        Executor executor = this.connector.getProtocolHandler().getExecutor();
//
//        if (executor instanceof ThreadPoolExecutor) {
//            ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor) executor;
//            threadPoolExecutor.shutdown();
//
//            try {
//                if (threadPoolExecutor.awaitTermination(shutdownTimeout, TimeUnit.SECONDS)) {
//                    log.warn("Tomcat thread pool did not shutdown within " + shutdownTimeout
//                            + " seconds. Proceeding forced shutdown.");
//                }
//            } catch (InterruptedException e) {
//                Thread.currentThread().interrupt();
//            }
//        }
//    }
//
//    @Override
//    public void customize(Connector connector) {
//        this.connector = connector;
//    }
//
//    @Override
//    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
//        this.applicationContext = applicationContext;
//    }
//
//    private void prepareShutdown() {
//        Callable mqShutDown = new mqShutDown();
//        FutureTask futureTask = new FutureTask(mqShutDown);
//        Thread thread = new Thread(futureTask);
//        thread.start();
//        while (!futureTask.isDone()) {
//            try {
//                Thread.sleep(500);
//                log.info("Proceeding consumer shutDown...");
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
//    }
//
//    private class mqShutDown implements Callable<Boolean> {
//        @Override
//        public Boolean call() throws Exception {
//            shutDownProducers();
//            shutDownConsumers();
//            return true;
//        }
//
//        private void shutDownConsumers() {
//            log.info("start to shutdown consumers.");
//            Map<String, Consumer> consumerMap = applicationContext.getBeansOfType(Consumer.class);
//            if (consumerMap != null && consumerMap.size() > 0) {
//                for (Consumer consumer: consumerMap.values()) {
//                    consumer.shutdown();
//                }
//            }
//            log.info("shutdown consumers success.");
//        }
//
//        private void shutDownProducers() {
//            log.info("start to shutdown producer.");
//            Map<String, Producer> producerMap = applicationContext.getBeansOfType(Producer.class);
//            if (producerMap != null && producerMap.size() > 0) {
//                for (Producer producer: producerMap.values()) {
//                    producer.stutdown();
//                }
//            }
//            log.info("shutdown producer success.");
//        }
//    }
//}