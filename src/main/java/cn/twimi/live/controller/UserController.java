package cn.twimi.live.controller;

import org.springframework.web.bind.annotation.*;
import cn.twimi.live.annotation.Permission;
import cn.twimi.live.common.ApiResponse;
import cn.twimi.live.common.PageData;
import cn.twimi.live.model.User;
import cn.twimi.live.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/user")
@CrossOrigin
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public ApiResponse<User> apiLogin(@RequestParam String username, @RequestParam String password) {
        return userService.login(username, password);
    }

    @Permission("admin")
    @PostMapping("/create")
    public ApiResponse<User> apiCreate(
            @RequestParam String username,
            @RequestParam String password,
            @RequestParam String name,
            @RequestParam(required = false, defaultValue = "") String email,
            @RequestParam(required = false, defaultValue = "") String phone,
            @RequestParam(required = false, defaultValue = "0") int roleId
    ) {
        User user = new User(username, password, name, email, phone);
        user.setRoleId(roleId);
        return userService.register(user);
    }

    @Permission("admin")
    @PostMapping("/list")
    public ApiResponse<PageData<User>> apiList(
            @RequestParam(name = "page", required = false, defaultValue = "1") int page,
            @RequestParam(name = "size", required = false, defaultValue = "10") int size) {
        PageData<User> userPageData = PageData.<User>builder()
                .list(userService.getUsersByPage(page, size)).page(page)
                .total(userService.countUser())
                .build();
        return ApiResponse.<PageData<User>>builder().status(0).msg("ok").data(userPageData).build();
    }

    @Permission("admin")
    @PostMapping("/updateState")
    public ApiResponse<Boolean> apiUpdateState(
            @RequestParam("id") long id,
            @RequestParam("state") int state) {
        return userService.updateState(id, state);
    }

    @Permission("login")
    @PostMapping("/userInfo")
    public ApiResponse<User> apiUserInfo(
            @RequestParam("id") long id
    ) {
        User user = userService.getUserById(id);
        if (user != null) {
            return ApiResponse.<User>builder().data(user).msg("ok").status(0).build();
        } else {
            return ApiResponse.<User>builder().msg("用户不存在").status(1002).build();
        }
    }
}
