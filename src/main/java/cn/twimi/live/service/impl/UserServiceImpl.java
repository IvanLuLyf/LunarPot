package cn.twimi.live.service.impl;

import org.springframework.stereotype.Service;
import cn.twimi.live.common.ApiResponse;
import cn.twimi.live.dao.UserDao;
import cn.twimi.live.model.User;
import cn.twimi.live.service.UserService;
import cn.twimi.live.util.MD5;

import java.util.Date;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private UserDao userDao;

    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    private String generateToken(String username) {
        return MD5.encode("BUNNY_" + Math.random() + "$" + username);
    }

    @Override
    public User getUserById(long id) {
        return userDao.getUserById(id);
    }

    @Override
    public User getUserByToken(String token) {
        User user = userDao.getUserByToken(token);
        if (user != null) {
            if ((new Date()).after(user.getExpire())) {
                return null;
            }
        }
        return user;
    }

    @Override
    public List<User> getUsersBySearch(String search) {
        return userDao.getUsersBySearch("%" + search + "%");
    }

    @Override
    public List<User> getUsersByPage(int page, int size) {
        return userDao.getUsersByPage((page - 1) * size, size);
    }

    @Override
    public int countUser() {
        return userDao.countUser();
    }

    @Override
    public ApiResponse<Boolean> updateState(long id, int state) {
        if (id == 1) {
            return ApiResponse.<Boolean>builder().status(1006).msg("无法修改管理员").build();
        }
        int res = userDao.updateState(id, state);
        if (res > 0) {
            return ApiResponse.<Boolean>builder().status(0).msg("ok").data(true).build();
        } else {
            return ApiResponse.<Boolean>builder().status(-6).msg("数据库出错").data(false).build();
        }
    }

    @Override
    public ApiResponse<Boolean> updateName(long id, String name) {
        int res = userDao.updateName(id, name);
        if (res > 0) {
            return ApiResponse.<Boolean>builder().status(0).msg("ok").data(true).build();
        } else {
            return ApiResponse.<Boolean>builder().status(-6).msg("数据库出错").data(false).build();
        }
    }

    @Override
    public ApiResponse<User> login(String username, String password) {
        User user = userDao.getUserByUsername(username);
        if (user == null) {
            return ApiResponse.<User>builder().status(1002).msg("用户不存在").build();
        }
        if (!user.getPassword().equals(MD5.encode(password))) {
            return ApiResponse.<User>builder().status(1001).msg("密码错误").build();
        }
        if (user.getState() != 1) {
            if (user.getState() == 0) {
                return ApiResponse.<User>builder().status(1005).msg("账号未启用").build();
            }
            if (user.getState() == 2) {
                return ApiResponse.<User>builder().status(1004).msg("账号已被禁用").build();
            }
        }
        if (null == user.getExpire() || (new Date()).after(user.getExpire())) {
            user.setExpire(new Date(System.currentTimeMillis() + 86400000L));
            user.setToken(generateToken(user.getUsername()));
        }
        user.setUpdateTime(new Date());
        if (userDao.updateToken(user) > 0) {
            return ApiResponse.<User>builder().status(0).msg("ok").data(user).build();
        } else {
            return ApiResponse.<User>builder().status(-6).msg("数据库出错").build();
        }
    }

    @Override
    public ApiResponse<User> register(User user) {
        User u = userDao.getUserByUsername(user.getUsername());
        if (u != null) {
            return ApiResponse.<User>builder().status(1003).msg("用户名已存在").build();
        }
        u = userDao.getUserByUsername(user.getEmail());
        if (u != null) {
            return ApiResponse.<User>builder().status(1003).msg("邮箱已存在").build();
        }
        user.setPassword(MD5.encode(user.getPassword()));
        user.setExpire(new Date(System.currentTimeMillis() + 86400000L));
        user.setToken(generateToken(user.getUsername()));
        int row = userDao.create(user);
        if (row <= 0) {
            return ApiResponse.<User>builder().status(-6).msg("数据库出错").build();
        }
        return ApiResponse.<User>builder().status(0).msg("ok").data(user).build();
    }
}
