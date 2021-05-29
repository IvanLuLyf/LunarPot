package cn.twimi.live.controller;

import cn.twimi.live.annotation.Permission;
import cn.twimi.live.common.ApiResponse;
import cn.twimi.live.model.FileInfo;
import cn.twimi.live.model.User;
import cn.twimi.live.service.FileService;
import cn.twimi.live.util.IPFSUtil;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MultipartFile;

@Controller
@CrossOrigin
@RequestMapping("/file")
public class FileController {
    @RequestMapping(value = "/p/{id}", produces = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE})
    @ResponseBody
    @Cacheable(value = "pictures", key = "#id")
    public ResponseEntity<byte[]> picture(@PathVariable("id") String id, WebRequest request) {
        long lastModified = System.currentTimeMillis() / 3600000 * 3600000;
        if (request.checkNotModified(lastModified)) {
            return null;
        }
        return ResponseEntity.ok().lastModified(lastModified).body(IPFSUtil.fetchFile(id));
    }

    @RequestMapping(value = "/t/{id}", produces = MediaType.TEXT_PLAIN_VALUE)
    @ResponseBody
    public byte[] text(@PathVariable("id") String id) {
        return IPFSUtil.fetchFile(id);
    }

    @RequestMapping(value = "/h/{id}", produces = MediaType.TEXT_HTML_VALUE)
    @ResponseBody
    public byte[] html(@PathVariable("id") String id) {
        return IPFSUtil.fetchFile(id);
    }

    @RequestMapping(value = "/a/{id}", produces = MediaType.ALL_VALUE)
    @ResponseBody
    public byte[] any(@PathVariable("id") String id) {
        return IPFSUtil.fetchFile(id);
    }

    @Permission(User.LOGIN)
    @RequestMapping("/upload")
    @ResponseBody
    public ApiResponse<String> upload(@RequestParam("file") MultipartFile file, FileService fileService) {
        FileInfo fileInfo = fileService.upload(file, "attach");
        return ApiResponse.<String>builder()
                .status(0)
                .msg("ok")
                .data(fileService.pathToUrl(fileInfo.getPath())).build();
    }
}
