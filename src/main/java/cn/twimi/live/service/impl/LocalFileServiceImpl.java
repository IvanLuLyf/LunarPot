package cn.twimi.live.service.impl;

import cn.twimi.live.model.FileInfo;
import cn.twimi.live.service.FileService;
import cn.twimi.live.util.MD5;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Objects;

@Service
@Profile("local")
public class LocalFileServiceImpl implements FileService {
    private File getDir(String subDir) throws FileNotFoundException {
        String basePath = ResourceUtils.getURL("classpath:").getPath() + File.separator + subDir;
        File dir = new File(basePath);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        return dir;
    }

    @Override
    public FileInfo upload(MultipartFile file, String subDir) {
        String path = "";
        try {
            String[] fs = Objects.requireNonNull(file.getOriginalFilename()).split("\\.");
            String filename = MD5.encode("res" + System.currentTimeMillis()) + "." + fs[fs.length - 1];
            File dir = getDir(subDir);
            File uploadFile = new File(dir.getAbsolutePath() + File.separator + filename);
            file.transferTo(uploadFile);
            path = subDir + "/" + filename;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return FileInfo.builder()
                .filename(file.getOriginalFilename())
                .path(path)
                .type("local")
                .build();
    }

    @Override
    public String pathToUrl(String path) {
        return "http://localhost:8080/upload/" + path;
    }
}
