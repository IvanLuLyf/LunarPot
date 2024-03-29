package cn.twimi.common.service.impl;

import cn.twimi.common.model.FileInfo;
import cn.twimi.common.service.FileService;
import cn.twimi.util.HashUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Objects;

@Service
@Profile({"local", "mysql"})
public class LocalFileServiceImpl implements FileService {
    @Value("${lunar-pot.hostname:}")
    private String hostname;

    private File getDir(String subDir) throws FileNotFoundException {
        String basePath = ResourceUtils.getURL("classpath:").getPath() + "upload" + File.separator + subDir;
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
            String filename = HashUtil.encode("res" + System.currentTimeMillis()) + "." + fs[fs.length - 1];
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
        return hostname + "/upload/" + path;
    }
}
