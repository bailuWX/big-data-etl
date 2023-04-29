package com.bigdata.omp.config;

import net.sf.ehcache.CacheManager;
import net.sf.ehcache.config.CacheConfiguration;
import org.springframework.cache.annotation.CachingConfigurer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.cache.interceptor.CacheErrorHandler;
import org.springframework.cache.interceptor.CacheResolver;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.cache.interceptor.SimpleKeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableCaching
public class EhCacheConfiguration implements CachingConfigurer {

    @Bean
    public CacheManager ehCacheManager() {
        CacheConfiguration cacheConfiguration = new CacheConfiguration();
        cacheConfiguration.setName("userSession");
        //超出内存最大值自动清除内容策略
        cacheConfiguration.setMemoryStoreEvictionPolicy("LRU");
        //缓存最大条数5000，设置为0时无限制
        cacheConfiguration.setMaxEntriesLocalHeap(5000);
        cacheConfiguration.setEternal(true);
//        cacheConfiguration.setEternal(false);
        //缓存有效期为30分钟
//        cacheConfiguration.setTimeToIdleSeconds(5 * 60 * 60);
        net.sf.ehcache.config.Configuration config = new net.sf.ehcache.config.Configuration();

        //可以创建多个cacheConfiguration，都添加到Config中
        config.addCache(cacheConfiguration);
        return CacheManager.newInstance(config);
    }

    @Bean
    public org.springframework.cache.CacheManager cacheManager() {
        return new EhCacheCacheManager(ehCacheManager());
    }

    @Bean
    public KeyGenerator keyGenerator() {
        return new SimpleKeyGenerator();
    }

    @Override
    public CacheResolver cacheResolver() {
        return null;
    }

    @Override
    public CacheErrorHandler errorHandler() {
        return null;
    }
}