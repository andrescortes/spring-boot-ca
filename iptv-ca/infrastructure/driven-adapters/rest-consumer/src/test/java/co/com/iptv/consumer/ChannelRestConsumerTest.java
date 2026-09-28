package co.com.iptv.consumer;


import co.com.iptv.consumer.channel.ChannelRestConsumer;
import co.com.iptv.iptv.M3uParse;
import co.com.iptv.model.channel.Channel;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.test.StepVerifier;

import java.io.IOException;
import java.util.Collections;

import static org.mockito.ArgumentMatchers.anyString;

@ExtendWith(MockitoExtension.class)
class ChannelRestConsumerTest {

    private ChannelRestConsumer channelRestConsumer;
    @Mock
    private M3uParse m3uParse;
    private static MockWebServer mockBackEnd;

    @AfterAll
    static void tearDown() throws IOException {
        mockBackEnd.shutdown();
    }

    @BeforeAll
    static void setUp() throws IOException {
        mockBackEnd = new MockWebServer();
        mockBackEnd.start();
    }

    @Test
    @DisplayName("Validate the function getChannels.")
    void validateTestGet() {
        var webClient = WebClient.builder().baseUrl(mockBackEnd.url("/").toString()).build();
        channelRestConsumer = new ChannelRestConsumer(webClient, m3uParse);
        mockBackEnd.enqueue(new MockResponse()
                .setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .setResponseCode(HttpStatus.OK.value())
                .setBody("#EXTM3U\n#EXTINF:-1,Channel 1\nhttp://stream1"));

        Mockito.when(m3uParse.parse(anyString())).thenReturn(Collections.singletonList(Channel.builder()
                .id("123")
                .groupTitle("Movies")
                .country("ARG")
                .tvgId("idfr")
                .logo("alsdjf")
                .url("https://test.m3.channels.com/free")
                .build()));

        var response = channelRestConsumer.getChannels();

        StepVerifier.create(response)
                .consumeNextWith(channel -> {
                    Assertions.assertEquals("123", channel.getId());
                    Assertions.assertEquals("Movies", channel.getGroupTitle());
                    Assertions.assertEquals("ARG", channel.getCountry());
                    Assertions.assertEquals("idfr", channel.getTvgId());
                    Assertions.assertEquals("https://test.m3.channels.com/free", channel.getUrl());
                })
                .verifyComplete();
    }
}