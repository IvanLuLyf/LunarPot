package cn.twimi.live.service;

import org.springframework.stereotype.Service;
import cn.twimi.live.common.ApiResponse;
import cn.twimi.live.model.User;

import java.util.List;

@Service
public interface UserService {
    User getUserById(long id);

    User getUserByToken(String token);

    List<User> getUsersBySearch(String search);

    List<User> getUsersByPage(int page, int size);

    int countUser();

    ApiResponse<Boolean> updateState(long id, int state);

    ApiResponse<User> login(String username, String password);

    ApiResponse<User> register(User user);
}
