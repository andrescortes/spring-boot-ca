package co.com.iptv.api.channel;

import co.com.iptv.api.channel.dto.request.ChannelRequest;
import co.com.iptv.model.channel.Channel;
import co.com.iptv.usecase.channel.ChannelUseCase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.reactive.function.server.ServerRequest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.anyString;

@ExtendWith(MockitoExtension.class)
class ChannelHandlerTest {

    @Mock
    ChannelUseCase useCase;
    @InjectMocks
    ChannelHandler handler;
    Channel channel;
    ChannelRequest request;

    @BeforeEach
    void setUp() {
        request = ChannelRequest.builder()
                .name("Test Channel")
                .group("Test Group")
                .page(1)
                .size(10)
                .build();
        channel = Channel.builder()
                .id("123")
                .name("Test Channel")
                .groupTitle("Test Group")
                .logo("http://example.com/logo.png")
                .country("Test Country")
                .tvgId("tvg123")
                .build();
    }

    @Test
    void getAllChannelsByAllParameters() {
        var sr = Mockito.mock(ServerRequest.class);
        Mockito.when(sr.bodyToMono(ChannelRequest.class)).thenReturn(Mono.just(request));

        Mockito.when(useCase.getChannels()).thenReturn(Flux.just(channel));

        StepVerifier.create(handler.getAllChannels(sr))
                .consumeNextWith(serverResponse -> {
                    boolean isSuccess = serverResponse.statusCode().is2xxSuccessful();
                    Assertions.assertTrue(isSuccess);
                })
                .verifyComplete();
    }

    @Test
    void getAllChannelsByGroup() {
        request.setName("");
        var sr = Mockito.mock(ServerRequest.class);
        Mockito.when(sr.bodyToMono(ChannelRequest.class)).thenReturn(Mono.just(request));

        Mockito.when(useCase.getChannels()).thenReturn(Flux.just(channel));

        StepVerifier.create(handler.getAllChannels(sr))
                .consumeNextWith(serverResponse -> {
                    boolean isSuccess = serverResponse.statusCode().is2xxSuccessful();
                    Assertions.assertTrue(isSuccess);
                })
                .verifyComplete();
    }

    @Test
    void getAllChanelGroups() {
        var sr = Mockito.mock(ServerRequest.class);
        Mockito.when(useCase.getChannels()).thenReturn(Flux.just(channel));

        StepVerifier.create(handler.getAllChanelGroups(sr))
                .consumeNextWith(sres -> Assertions.assertTrue(sres.statusCode().is2xxSuccessful()))
                .verifyComplete();
    }

    @Test
    void getChannelById() {
        var sr = Mockito.mock(ServerRequest.class);

        Mockito.when(sr.pathVariable(anyString())).thenReturn("123");
        Mockito.when(useCase.getChannels()).thenReturn(Flux.just(channel));

        StepVerifier.create(handler.getChannelById(sr))
                .consumeNextWith(serverResponse -> {
                    Assertions.assertTrue(serverResponse.statusCode().is2xxSuccessful());
                })
                .verifyComplete();
    }

    @Test
    void getChannelByIdNotFound() {
        var sr = Mockito.mock(ServerRequest.class);

        Mockito.when(sr.pathVariable(anyString())).thenReturn("124");
        Mockito.when(useCase.getChannels()).thenReturn(Flux.just(channel));

        StepVerifier.create(handler.getChannelById(sr))
                .consumeNextWith(serverResponse -> {
                    Assertions.assertTrue(serverResponse.statusCode().is4xxClientError());
                })
                .verifyComplete();
    }
}