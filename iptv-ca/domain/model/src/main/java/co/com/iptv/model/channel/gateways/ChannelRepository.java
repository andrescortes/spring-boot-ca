package co.com.iptv.model.channel.gateways;

import reactor.core.publisher.Mono;

public interface ChannelRepository {

    Mono<String> getChannels();
}
