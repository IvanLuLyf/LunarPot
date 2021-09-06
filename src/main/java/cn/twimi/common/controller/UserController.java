package cn.twimi.common.controller;

import cn.twimi.common.annotation.Permission;
import cn.twimi.util.ApiResponse;
import cn.twimi.util.PageData;
import cn.twimi.common.model.User;
import cn.twimi.common.service.UserService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/api/user")
@CrossOrigin
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public ApiResponse<User> apiLogin(@RequestParam String username, @RequestParam String password) {
        return userService.login(username, password);
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    @ResponseBody
    public ApiResponse<User> apiRegister(
            @RequestParam String username,
            @RequestParam String password,
            @RequestParam String name,
            @RequestParam(required = false, defaultValue = "") String email,
            @RequestParam(required = false, defaultValue = "") String phone
    ) {
        User user = new User(username, password, name, email, phone);
        user.setRoleId(0);
        return userService.register(user);
    }

    @Permission(User.ADMIN)
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

    @Permission(User.ADMIN)
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

    @Permission(User.ADMIN)
    @PostMapping("/updateState")
    public ApiResponse<Boolean> apiUpdateState(
            @RequestParam("id") long id,
            @RequestParam("state") int state) {
        return userService.updateState(id, state);
    }

    @Permission(User.LOGIN)
    @PostMapping("/updateName")
    public ApiResponse<Boolean> apiUpdateName(
            HttpServletRequest request,
            @RequestParam("name") String name
    ) {
        User user = (User) request.getAttribute("curUser");
        return userService.updateName(user.getId(), name);
    }

    @Permission(User.LOGIN)
    @PostMapping("/updateAvatar")
    public ApiResponse<String> apiUpdateAvatar(
            HttpServletRequest request,
            @RequestParam("file") MultipartFile file
    ) {
        User user = (User) request.getAttribute("curUser");
        return userService.updateAvatar(user.getId(), file);
    }

    @Permission(User.LOGIN)
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

    @PostMapping("/search")
    public ApiResponse<PageData<User>> apiSearch(
            @RequestParam(name = "keyword") String keyword,
            @RequestParam(name = "page", required = false, defaultValue = "1") int page,
            @RequestParam(name = "size", required = false, defaultValue = "10") int size) {
        List<User> users = userService.getUsersBySearch(keyword, page, size);
        PageData<User> userPageData = PageData.<User>builder()
                .list(users).page(page)
                .total(users.size())
                .build();
        return ApiResponse.<PageData<User>>builder().status(0).msg("ok").data(userPageData).build();
    }
}
