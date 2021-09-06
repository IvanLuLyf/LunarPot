package cn.twimi.common.service;

import org.springframework.stereotype.Service;
import cn.twimi.util.ApiResponse;
import cn.twimi.common.model.User;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public interface UserService {
    User getUserById(long id);

    User getUserByToken(String token);

    List<User> getUsersBySearch(String search,int page, int size);

    List<User> getUsersByPage(int page, int size);

    int countUser();

    ApiResponse<Boolean> updateState(long id, int state);

    ApiResponse<Boolean> updateName(long id, String name);

    ApiResponse<String> updateAvatar(long id, MultipartFile file);

    ApiResponse<User> login(String username, String password);

    ApiResponse<User> register(User user);
}
