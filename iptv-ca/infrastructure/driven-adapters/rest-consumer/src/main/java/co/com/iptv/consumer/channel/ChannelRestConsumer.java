package co.com.iptv.consumer.channel;

import co.com.iptv.model.channel.gateways.ChannelRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class ChannelRestConsumer implements ChannelRepository {

    private final WebClient client;

    @CircuitBreaker(name = "channelsGet")
    @Override
    public Mono<String> getChannels() {
        return client
                .get()
                .retrieve()
                .bodyToMono(String.class);
    }
}
