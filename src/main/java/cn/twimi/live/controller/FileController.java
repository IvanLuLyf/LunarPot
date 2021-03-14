package cn.twimi.live.controller;

import cn.twimi.live.util.IPFSUtil;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;

@Controller
@CrossOrigin
@RequestMapping("/file")
public class FileController {
    @RequestMapping(value = "/p/{id}", produces = {MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE})
    @ResponseBody
    @Cacheable(value = "pictures", key = "#id")
    public ResponseEntity<byte[]> picture(@PathVariable("id") String id) {
        return ResponseEntity.ok().lastModified(System.currentTimeMillis()).body(IPFSUtil.fetchFile(id));
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
}
