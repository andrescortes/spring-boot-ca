package co.com.iptv.usecase.channel;

import co.com.iptv.model.channel.Channel;
import co.com.iptv.model.channel.gateways.ChannelRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;

@RequiredArgsConstructor
public class ChannelUseCase {

    private final ChannelRepository channelRepository;

    public Flux<Channel> getChannels() {
        return channelRepository.getChannels();
    }
}
