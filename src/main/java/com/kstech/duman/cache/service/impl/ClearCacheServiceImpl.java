package com.kstech.duman.cache.service.impl;

import com.hazelcast.spring.cache.HazelcastCache;
import com.hazelcast.spring.cache.HazelcastCacheManager;
import com.kstech.duman.cache.service.ClearCacheService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class ClearCacheServiceImpl implements ClearCacheService {
    private final CacheManager cacheManager;

    public ClearCacheServiceImpl(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

    @Override
    public void clearCacheByServiceNames(List<String> serviceNames) {
        HazelcastCacheManager hzCacheManager = (HazelcastCacheManager) this.cacheManager;
        HazelcastCache cache = (HazelcastCache) hzCacheManager.getCache("cache");

        List<String> keysToRemove = cache.getNativeCache()
                .keySet().stream()
                .map(Object::toString)
                .filter(entryKey -> {
                    if (serviceNames.isEmpty()) {
                        return true;
                    } else {
                        boolean result = false;
                        for (String serviceName : serviceNames) {
                            if (entryKey.equals(serviceName)) {
                                result = true;
                            }
                        }
                        return result;
                    }
                }).collect(Collectors.toList());
        try {
            keysToRemove.forEach(keyToRemove -> cache.getNativeCache().remove(keyToRemove));
        } catch (Exception ex) {
            log.info("ClassNotFoundException when cache remove");
        }
    }
}
