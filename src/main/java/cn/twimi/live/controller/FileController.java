package cn.twimi.live.controller;

import cn.twimi.live.util.IPFSUtil;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/file")
public class FileController {
    @RequestMapping(value = "/{id}", produces = MediaType.ALL_VALUE)
    @ResponseBody
    public byte[] test(@PathVariable("id") String id) throws Exception {
        return IPFSUtil.fetchFile(id);
    }
}
