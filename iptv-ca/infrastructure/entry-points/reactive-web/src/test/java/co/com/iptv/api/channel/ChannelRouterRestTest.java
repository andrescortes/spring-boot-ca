package co.com.iptv.api.channel;

import co.com.iptv.api.channel.dto.request.ChannelRequest;
import co.com.iptv.model.channel.Channel;
import co.com.iptv.model.channel.ChannelGroup;
import co.com.iptv.usecase.channel.ChannelUseCase;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webflux.test.autoconfigure.WebFluxTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.util.DefaultUriBuilderFactory;
import org.springframework.web.util.UriBuilderFactory;
import reactor.core.publisher.Flux;
import tools.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.Map;

@Slf4j
@ContextConfiguration(classes = {ChannelRouterRest.class, ChannelHandler.class})
@WebFluxTest
class ChannelRouterRestTest {

    @MockitoBean
    private ChannelUseCase useCase;

    @Autowired
    private WebTestClient client;

    @BeforeEach
    void setUp() {
        Mockito.when(useCase.getChannels()).thenReturn(Flux.just(Channel.builder()
                .id("123")
                .name("Test Channel")
                .groupTitle("Test Group")
                .logo("http://example.com/logo.png")
                .country("Test Country")
                .language("English")
                .tvgId("tvg123")
                .build()));
    }

    @Test
    void getChannels() {
        var channelRequest = ChannelRequest.builder()
                .page(1)
                .size(5)
                .build();
        client
                .post()
                .uri("/api/channels")
                .bodyValue(channelRequest)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(Channel.class)
                .value(channels -> {
                    logResponse(channels);
                    Assertions.assertNotNull(channels);
                    Assertions.assertEquals(1, channels.size());
                    Channel channel = channels.get(0);
                    Assertions.assertEquals("123", channel.getId());
                    Assertions.assertEquals("Test Channel", channel.getName());
                });
    }

    @Test
    void getChannelsWithQueryParams() {

        client
                .get()
                .uri(uriBuilder -> uriBuilder.path("/api/channels")
                        .queryParam("group", "Test Group")
                        .queryParam("name", "Test Channel")
                        .build()
                )
                .exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(Channel.class)
                .value(channels -> {
                    logResponse(channels);
                    Assertions.assertNotNull(channels);
                    Assertions.assertEquals(1, channels.size());
                    Channel channel = channels.get(0);
                    Assertions.assertEquals("123", channel.getId());
                    Assertions.assertEquals("Test Channel", channel.getName());
                });
    }

    @Test
    void getChannelById() {
        String channelId = "123"; // Reemplaza con un ID válido para tu prueba
        client
                .get()
                .uri("/api/channels/{id}", channelId)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Channel.class)
                .value(response -> {
                    logResponse(response);
                    Assertions.assertNotNull(response);
                    Assertions.assertEquals(channelId, response.getId());
                    Assertions.assertEquals("Test Channel", response.getName());
                });
    }

    @Test
    void getGroups() {
        var channels = Flux.fromIterable(List.of(
                Channel.builder()
                        .id("123")
                        .name("Test Channel")
                        .groupTitle("Metallica")
                        .logo("http://example.com/logo.png")
                        .country("Test Country")
                        .language("English")
                        .tvgId("tvg123")
                        .build(),
                Channel.builder()
                        .id("456")
                        .name("Another Channel")
                        .groupTitle("Peace")
                        .logo("http://example.com/logo2.png")
                        .country("Test Country")
                        .language("English")
                        .tvgId("tvg456")
                        .build()
        ));
        Mockito.when(useCase.getChannels()).thenReturn(channels);
        client
                .get()
                .uri("/api/channel-groups")
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(ChannelGroup.class)
                .value(response -> {
                    logResponse(response);
                    Assertions.assertNotNull(response);
                    Assertions.assertEquals(2, response.size());
                    ChannelGroup group = response.get(0);
                    Assertions.assertEquals(1, group.getChannelsCount());
                    Assertions.assertEquals("Peace", group.getName());

                    ChannelGroup channelGroup = response.get(1);
                    Assertions.assertEquals("Metallica", channelGroup.getName());
                    Assertions.assertEquals(1, channelGroup.getChannelsCount());
                });
    }

    private <T> void logResponse(T response) {
        try {
            String prettyResponse = new ObjectMapper().writerWithDefaultPrettyPrinter().writeValueAsString(response);
            log.info("Received response: {}", prettyResponse);
        } catch (Exception e) {
            log.error("Error logging response", e);
        }
    }
}