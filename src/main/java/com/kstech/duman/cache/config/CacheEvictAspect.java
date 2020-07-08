package com.kstech.duman.cache.config;

import com.hazelcast.spring.cache.HazelcastCache;
import com.hazelcast.spring.cache.HazelcastCacheManager;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.aop.aspectj.MethodInvocationProceedingJoinPoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;

import java.util.List;
import java.util.stream.Collectors;

@Aspect
@Slf4j
public class CacheEvictAspect {
    @Autowired
    private CacheManager cacheManager;

    @Pointcut("@annotation(org.springframework.cache.annotation.CacheEvict)")
    public void cacheEvictPointcut() {
    }

    @After("cacheEvictPointcut()")
    public void logAfterGetEmployee(JoinPoint joinPoint) {
        MethodInvocationProceedingJoinPoint x = (MethodInvocationProceedingJoinPoint) joinPoint;
        String mainClassPackage = x.getSourceLocation().getWithinType().getSimpleName();

        HazelcastCacheManager hzCacheManager = (HazelcastCacheManager) this.cacheManager;
        HazelcastCache cache = (HazelcastCache) hzCacheManager.getCache("cache");

        List<String> keysToRemove = cache.getNativeCache()
                .keySet().stream()
                .map(Object::toString)
                .filter(entryKey -> entryKey.contains(mainClassPackage))
                .collect(Collectors.toList());

        keysToRemove.forEach(keyToRemove -> cache.getNativeCache().remove(keyToRemove));
    }
}
