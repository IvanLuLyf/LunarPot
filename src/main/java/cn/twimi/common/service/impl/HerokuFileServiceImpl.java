package cn.twimi.common.service.impl;

import cn.twimi.common.model.FileInfo;
import cn.twimi.common.service.FileService;
import cn.twimi.util.IPFSUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;

@Service
@Profile("heroku")
public class HerokuFileServiceImpl implements FileService {
    @Value("${lunar-pot.hostname:}")
    private String hostname;

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
        return hostname + "/file/p/" + path;
    }
}
