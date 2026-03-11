package co.com.iptv.api.channel;

import co.com.iptv.api.channel.dto.request.ChannelRequest;
import co.com.iptv.model.channel.Channel;
import co.com.iptv.model.channel.ChannelGroup;
import co.com.iptv.usecase.channel.ChannelUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.NonNull;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Objects;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class ChannelHandler {

    private final ChannelUseCase channelUseCase;

    private static @NonNull Flux<Channel> getFilterByGroup(Flux<Channel> channels, String group) {
        return channels
                .filter(channel -> {
                    String groupName = Optional.ofNullable(channel)
                            .map(chan -> chan.getGroupTitle())
                            .orElse("");
                    log.info("Group: {}, GroupName: {}", group, groupName);
                    return group.equalsIgnoreCase(groupName);
                });
    }

    private static @NonNull Flux<Channel> getFilterByName(Flux<Channel> channels, String name) {
        return channels
                .filter(channel -> {
                    String channelName = Optional.ofNullable(channel)
                            .map(chan -> chan.getName())
                            .orElse("");
                    log.info("Name: {}, ChannelName: {}", name, channelName);
                    return name.equalsIgnoreCase(channelName);
                });
    }

    // TODO: use @Caching for getGroups and getChannels
    // TODO: use @Caching for page and size parameters in getChannels
    public Mono<ServerResponse> getAllChannels(ServerRequest request) {
        return request.bodyToMono(ChannelRequest.class)
                .flatMap(req -> {
                    var group = Optional.ofNullable(req.getGroup()).orElse(null);
                    var name = Optional.ofNullable(req.getName()).orElse(null);
                    var page = Optional.ofNullable(req.getPage()).orElse(1);
                    var size = Optional.ofNullable(req.getSize()).orElse(10);
                    log.info("Received request with group: {} and name: {}", group, name);
                    log.info("Received request with page: {} and size: {}", page, size);
                    Flux<Channel> channels = channelUseCase.getChannels();

                    if (Objects.nonNull(group) && !group.isBlank()) {
                        channels = getFilterByGroup(channels, group);
                    }

                    if (Objects.nonNull(name) && !name.isBlank()) {
                        channels = getFilterByName(channels, name);
                    }
                    channels = channels.skip((long) (page - 1) * size).take(size);
                    return ServerResponse
                            .ok()
                            .contentType(MediaType.APPLICATION_JSON)
                            .body(channels, Channel.class);
                });
    }

    public Mono<ServerResponse> getChannelById(ServerRequest request) {
        String id = request.pathVariable("id");
        var channels = channelUseCase.getChannels();

        return channels
                .filter(channel -> channel.getId().equalsIgnoreCase(id))
                .singleOrEmpty()
                .flatMap(channel -> ServerResponse.ok()
                        .contentType(MediaType.APPLICATION_JSON)
                        .bodyValue(channel))
                .switchIfEmpty(ServerResponse.notFound().build());
    }

    public Mono<ServerResponse> getGroups(ServerRequest request) {
        Flux<ChannelGroup> groups = channelUseCase.getChannels()
                .groupBy(c -> Objects.nonNull(c) ? c.getGroupTitle() : "Unknown")
                .flatMap(groupedFlux -> groupedFlux
                        .count()
                        .map(count -> new ChannelGroup(groupedFlux.key(), Math.toIntExact(count)))
                );
        return ServerResponse
                .ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(groups, ChannelGroup.class);
    }
}
