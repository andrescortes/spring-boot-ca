package co.com.iptv.usecase.channel;

import co.com.iptv.model.channel.Channel;
import co.com.iptv.model.channel.gateways.ChannelRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

@ExtendWith(MockitoExtension.class)
class ChannelUseCaseTest {
    @Mock
    ChannelRepository repository;
    @InjectMocks
    ChannelUseCase useCase;

    @Test
    void getChannels() {
        Channel channel = Channel.builder()
                .id("123")
                .name("Channel 1")
                .country("Country")
                .url("https://www.google.com")
                .logo("https://www.google.com")
                .build();

        Mockito.when(repository.getChannels()).thenReturn(Flux.just(channel));

        StepVerifier.create(useCase.getChannels())
                .consumeNextWith(chanl -> {
                    Assertions.assertEquals(channel.getId(), chanl.getId());
                    Assertions.assertEquals(channel.getName(), chanl.getName());
                    Assertions.assertEquals(channel.getCountry(), chanl.getCountry());
                    Assertions.assertEquals(channel.getUrl(), chanl.getUrl());
                    Assertions.assertEquals(channel.getLogo(), chanl.getLogo());
                })
                .verifyComplete();
    }
}