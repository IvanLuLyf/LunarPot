package cn.twimi.response;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@JsonSerialize
@Data
@Builder
public class PageData<T> {
    private int total;
    private int page;
    private List<T> list;
}
