package cn.twimi.common.service;

import org.springframework.web.multipart.MultipartFile;
import cn.twimi.common.model.FileInfo;

public interface FileService {
    FileInfo upload(MultipartFile file, String subDir);

    String pathToUrl(String path);
}
