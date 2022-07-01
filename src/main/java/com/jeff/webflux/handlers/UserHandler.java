package com.jeff.webflux.handlers;

import com.jeff.webflux.constant.User;
import com.jeff.webflux.repository.UserDao;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

/**
 * @program: WebFlux
 * @author: Jeff Hu 2022/07/01 15:46
 */
@Component
public class UserHandler {

    private final UserDao userDao;

    public UserHandler(UserDao userDao) {
        this.userDao = userDao;
    }

    /**
     * 得到所有用户
     *
     * @param request
     * @return
     */
    public Mono<ServerResponse> getAllUsers(ServerRequest request) {
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(userDao.findAll(), User.class);
    }

    /**
     * 创建用户
     *
     * @param request
     * @return
     */
    public Mono<ServerResponse> createUser(ServerRequest request) {
        Mono<User> user = request.bodyToMono(User.class);
        return ServerResponse.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(userDao.saveAll(user), User.class);
    }

    /**
     * 根据 ID 删除用户
     * @param request
     * @return
     */
    public Mono<ServerResponse> deleteUser(ServerRequest request) {
        String id = request.pathVariable("id");
        return userDao.findById(id)
                .flatMap(user -> userDao.delete(user).then(ServerResponse.ok().build()))
                .switchIfEmpty(ServerResponse.notFound().build());
    }
}
