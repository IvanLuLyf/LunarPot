package cn.twimi.live.controller;

import cn.twimi.live.util.IPFSUtil;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@CrossOrigin
@RequestMapping("/file")
public class FileController {
    @RequestMapping(value = "/p/{id}", produces = MediaType.IMAGE_JPEG_VALUE)
    @ResponseBody
    public byte[] picture(@PathVariable("id") String id) {
        return IPFSUtil.fetchFile(id);
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
