package co.com.app.model.userapp.gateways;

import co.com.app.model.userapp.UserApp;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserAppRepository {
    Mono<UserApp> createUser(UserApp userApp);

    Mono<UserApp> updateUser(Long userId, UserApp userApp);

    Flux<UserApp> findAllUsers();

    Mono<Boolean> deleteUser(Long userId);

    Mono<UserApp> findUserById(Long userId);
}
