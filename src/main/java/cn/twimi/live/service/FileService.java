package cn.twimi.live.service;

import org.springframework.web.multipart.MultipartFile;
import cn.twimi.live.model.FileInfo;

public interface FileService {
    FileInfo upload(MultipartFile file, String subDir);

    String pathToUrl(String path);
}
