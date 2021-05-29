package cn.twimi.live.controller;

import cn.twimi.live.annotation.Permission;
import cn.twimi.live.common.ApiResponse;
import cn.twimi.live.model.FileInfo;
import cn.twimi.live.model.User;
import cn.twimi.live.service.FileService;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/attach")
@CrossOrigin
public class AttachController {

    @Permission(User.LOGIN)
    @RequestMapping("/upload")
    public ApiResponse<String> upload(@RequestParam("file") MultipartFile file, FileService fileService) {
        FileInfo fileInfo = fileService.upload(file, "attach");
        return ApiResponse.<String>builder()
                .status(0)
                .msg("ok")
                .data(fileService.pathToUrl(fileInfo.getPath())).build();
    }
}
