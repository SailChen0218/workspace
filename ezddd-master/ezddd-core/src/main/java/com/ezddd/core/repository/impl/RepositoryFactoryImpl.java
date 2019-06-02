//package com.ezddd.core.repository.impl;
//
//import com.ezddd.core.annotation.EzComponent;
//import com.ezddd.core.repository.Repository;
//import com.ezddd.core.repository.RepositoryFactory;
//import com.ezddd.core.tunnel.Tunnel;
//import com.ezddd.core.tunnel.TunnelRegistry;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import java.util.Map;
//import java.util.concurrent.ConcurrentHashMap;
//
//@EzComponent
//public class RepositoryFactoryImpl implements RepositoryFactory {
//    private static Map<String, Repository> repositoryHolder = new ConcurrentHashMap<>();
//
//    @Autowired
//    TunnelRegistry tunnelRegistry;
//
//    @Override
//    public <T> Repository<T> getRepository(Class<?> aggregateType) {
//        Repository<T> repository = null;
//        if (!repositoryHolder.containsKey(aggregateType.getName())) {
//            Tunnel tunnel= tunnelRegistry.findTunnel(aggregateType);
//            if (tunnel == null) {
//                throw new IllegalArgumentException("tunnel not found. aggregateType:" + aggregateType.getName());
//            }
//            repository = new AbstractCachedRepository(aggregateType, tunnel);
//            repositoryHolder.put(aggregateType.getName(), repository);
//        } else {
//            repository = repositoryHolder.get(aggregateType.getName());
//        }
//        return repository;
//    }
//}
