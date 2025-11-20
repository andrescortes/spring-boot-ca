package co.com.app.api.userapp;

import co.com.app.api.userapp.dto.UserAppDto;
import co.com.app.api.userapp.mapper.UserMapper;
import co.com.app.usecase.userapp.UserAppUseCase;
import com.fasterxml.jackson.core.type.TypeReference;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserAppHandler {
    private final UserAppUseCase useCase;
    private final UserMapper mapper;

    public Mono<ServerResponse> findAll(ServerRequest request) {
        return ServerResponse
                .ok()
                .body(useCase.findAllUsers(), TypeReference.class);
    }

    public Mono<ServerResponse> addUser(ServerRequest request) {
        return request.bodyToMono(UserAppDto.class)
                .map(mapper::toEntity)
                .flatMap(useCase::addUser)
                .flatMap(userApp -> ServerResponse
                        .status(201)
                        .bodyValue(mapper.toDto(userApp))
                );
    }

    public Mono<ServerResponse> updateUser(ServerRequest request) {
        String userId = request.pathVariable("id");
        return request.bodyToMono(UserAppDto.class)
                .map(mapper::toEntity)
                .flatMap(userApp -> useCase.updateUser(Long.parseLong(userId), userApp))
                .flatMap(userApp -> ServerResponse
                        .status(200)
                        .bodyValue(mapper.toDto(userApp))
                );
    }

    public Mono<ServerResponse> deleteUser(ServerRequest request) {
        String userId = request.pathVariable("id");
        return useCase.deleteUser(Long.parseLong(userId))
                .flatMap(result -> ServerResponse.ok()
                        .bodyValue(result)
                );
    }

    public Mono<ServerResponse> findUserById(ServerRequest request) {
        Long userId = Long.parseLong(request.pathVariable("id"));

        return useCase.findUserById(userId)
                .flatMap(user -> ServerResponse.ok()
                        .bodyValue(mapper.toDto(user))
                );
    }
}
