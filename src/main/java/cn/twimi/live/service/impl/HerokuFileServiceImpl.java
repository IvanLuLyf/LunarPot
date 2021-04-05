package cn.twimi.live.service.impl;

import cn.twimi.live.model.FileInfo;
import cn.twimi.live.service.FileService;
import cn.twimi.live.util.IPFSUtil;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;

@Service
@Profile("heroku")
public class HerokuFileServiceImpl implements FileService {
    @Override
    public FileInfo upload(MultipartFile file, String subDir) {
        String path = "";
        try {
            path = IPFSUtil.uploadFile(file.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return FileInfo.builder()
                .filename(file.getOriginalFilename())
                .path(path)
                .type("ipfs")
                .build();
    }

    @Override
    public String pathToUrl(String path) {
        return "https://onelive.herokuapp.com/file/p/" + path;
    }
}
