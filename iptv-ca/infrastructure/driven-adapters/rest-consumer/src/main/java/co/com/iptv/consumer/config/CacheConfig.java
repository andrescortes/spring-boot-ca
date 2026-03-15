package co.com.iptv.consumer.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@EnableCaching
@Configuration
public class CacheConfig {
    public static final String ChannelCache = "channels";

    @Bean
    public CacheManager cacheManager() {
        var cacheManager = new CaffeineCacheManager(ChannelCache);
        cacheManager.setCaffeine(Caffeine.newBuilder()
                .expireAfterWrite(10, TimeUnit.MINUTES)
                .maximumSize(1000)
        );
        cacheManager.setAsyncCacheMode(true);
        return cacheManager;
    }
}
