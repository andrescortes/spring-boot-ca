package co.com.iptv.api.channel;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class ChannelRouterRest {

    @Bean
    public RouterFunction<ServerResponse> channelRoutes(ChannelHandler handler) {
        return RouterFunctions.route()
                .path("/api", builder -> builder
                        .nest(RequestPredicates.accept(MediaType.APPLICATION_JSON),
                                nested -> nested
                                        .POST("/channels", handler::getAllChannels)
                                        .GET("/channels/{id}", handler::getChannelById)
                                        .GET("/channel-groups", handler::getGroups)
                        )
                )
                .build();
    }
}
