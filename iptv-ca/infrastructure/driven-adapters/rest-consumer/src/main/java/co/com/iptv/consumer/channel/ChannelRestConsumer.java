package co.com.iptv.consumer.channel;

import co.com.iptv.consumer.config.CacheConfig;
import co.com.iptv.iptv.M3uParse;
import co.com.iptv.model.channel.Channel;
import co.com.iptv.model.channel.gateways.ChannelRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import java.time.Duration;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChannelRestConsumer implements ChannelRepository {

    private final WebClient client;
    private final M3uParse m3uParse;

    @Cacheable(cacheNames = CacheConfig.ChannelCache, key = "'all'")
    @CircuitBreaker(name = "channelsGet")
    @Override
    public Flux<Channel> getChannels() {
        log.info("Calling to API IPTV without Cache");
        return client
                .get()
                .retrieve()
                .bodyToMono(String.class)
                .retry(3)
                .flatMapMany(m3u -> Flux.fromIterable(m3uParse.parse(m3u)))
                .cache(Duration.ofMinutes(10));
    }
}
