package co.com.app.api.userapp;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.DELETE;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.PATCH;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Slf4j
@Configuration
public class UserAppRouterRest {
    @Bean
    public RouterFunction<ServerResponse> userAppRouterFunction(UserAppHandler handler) {
        return route(GET("/users"), handler::findAll)
                .andRoute(GET("/users/{id}"), handler::findUserById)
                .andRoute(POST("/users"), handler::addUser)
                .andRoute(PATCH("/users/{id}"), handler::updateUser)
                .andRoute(DELETE("/users/{id}"), handler::deleteUser);
    }
}
