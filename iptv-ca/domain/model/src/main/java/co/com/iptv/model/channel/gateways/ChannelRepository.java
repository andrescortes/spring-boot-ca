package co.com.iptv.model.channel.gateways;

import co.com.iptv.model.channel.Channel;
import reactor.core.publisher.Flux;

public interface ChannelRepository {

    Flux<Channel> getChannels();
}
