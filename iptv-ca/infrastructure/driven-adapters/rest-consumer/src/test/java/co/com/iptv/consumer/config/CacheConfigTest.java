package co.com.iptv.consumer.config;

import org.junit.jupiter.api.Test;
import org.springframework.cache.CacheManager;
import org.springframework.cache.caffeine.CaffeineCacheManager;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class CacheConfigTest {

    private final CacheConfig config = new CacheConfig();

    @Test
    void cacheManager_isConfiguredWithExpectedCacheName() {
        CacheManager cacheManager = config.cacheManager();

        assertThat(cacheManager).isInstanceOf(CaffeineCacheManager.class);
        assertThat(cacheManager.getCacheNames()).containsExactly(CacheConfig.ChannelCache);
        assertThat(cacheManager.getCache(CacheConfig.ChannelCache)).isNotNull();
    }
}