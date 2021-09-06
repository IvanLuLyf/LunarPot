package cn.twimi.live.controller;

import cn.twimi.util.ApiResponse;
import cn.twimi.common.model.FileInfo;
import cn.twimi.common.service.FileService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/attach")
@CrossOrigin
public class AttachController {

    private final FileService fileService;

    public AttachController(FileService fileService) {
        this.fileService = fileService;
    }

    @RequestMapping("/upload")
    public ApiResponse<String> upload(@RequestParam("file") MultipartFile file) {
        FileInfo fileInfo = fileService.upload(file, "attach");
        return ApiResponse.<String>builder()
                .status(0)
                .msg("ok")
                .data(fileService.pathToUrl(fileInfo.getPath())).build();
    }
}
