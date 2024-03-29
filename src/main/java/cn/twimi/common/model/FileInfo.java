package cn.twimi.common.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FileInfo {
    private String filename;
    private String path;
    private String type;
}
