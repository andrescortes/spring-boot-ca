package co.com.app.usecase.userapp;

import co.com.app.model.userapp.UserApp;
import co.com.app.model.userapp.gateways.UserAppRepository;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class UserAppUseCase {
    private final UserAppRepository repository;

    public Mono<UserApp> addUser(UserApp userApp) {
        return repository.createUser(userApp);
    }

    public Mono<UserApp> updateUser(Long userId, UserApp userApp) {
        return repository.updateUser(userId, userApp);
    }

    public Flux<UserApp> findAllUsers() {
        return repository.findAllUsers();
    }

    public Mono<Boolean> deleteUser(Long userId) {
        return repository.deleteUser(userId);
    }

    public Mono<UserApp> findUserById(Long userId) {
        return repository.findUserById(userId);
    }
}
