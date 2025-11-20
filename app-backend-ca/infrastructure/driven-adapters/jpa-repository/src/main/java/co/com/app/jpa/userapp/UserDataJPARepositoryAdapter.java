package co.com.app.jpa.userapp;

import co.com.app.jpa.helper.AdapterOperations;
import co.com.app.jpa.userapp.data.UserData;
import co.com.app.model.userapp.UserApp;
import co.com.app.model.userapp.gateways.UserAppRepository;
import org.reactivecommons.utils.ObjectMapper;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public class UserDataJPARepositoryAdapter extends AdapterOperations<UserApp/* change for domain model */, UserData/* change for adapter model */, Long, UserDataJPARepository>
        implements UserAppRepository {


    public UserDataJPARepositoryAdapter(UserDataJPARepository repository, ObjectMapper mapper) {
        /**
         *  Could be use mapper.mapBuilder if your domain model implement builder pattern
         *  super(repository, mapper, d -> mapper.mapBuilder(d,ObjectModel.ObjectModelBuilder.class).build());
         *  Or using mapper.map with the class of the object model
         */
        super(repository, mapper, d -> mapper.map(d, UserApp.class/* change for domain model */));
    }

    @Override
    public Mono<UserApp> createUser(UserApp userApp) {
        return Mono.defer(() -> Mono.just(userApp))
                .map(user -> super.save(userApp));
    }

    @Override
    public Mono<UserApp> updateUser(Long userId, UserApp userApp) {
        return Mono.just(super.findById(userId))
                .flatMap(data -> {
                    data.setName(userApp.getName());
                    data.setEmail(userApp.getEmail());
                    data.setCreatedAt(userApp.getCreatedAt());
                    return Mono.just(super.save(data));
                });
    }

    @Override
    public Flux<UserApp> findAllUsers() {
        return Flux.defer(() -> Flux.fromIterable(this.findAll()));
    }

    @Override
    public Mono<Boolean> deleteUser(Long userId) {
        try {
            repository.deleteById(userId);
            return Mono.just(true);
        } catch (Exception e) {
            return Mono.just(false);
        }
    }

    @Override
    public Mono<UserApp> findUserById(Long userId) {
        return Mono.defer(() -> Mono.just(super.findById(userId)));
    }
}
