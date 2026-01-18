package com.example.template.common.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PageRequestDto {
    private int page;
    private int size;
    private String keyword;
    private String searchType;

    public PageRequestDto() {
        this.page = 1;
        this.size = 10;
        this.keyword = "";
        this.searchType = "";
    }

    public PageRequestDto(int page, int size) {
        this.page = page <= 0 ? 1 : page;
        this.size = size <= 0 ? 10 : size;
        this.keyword = "";
        this.searchType = "";
    }

    public PageRequestDto(int page, int size, String keyword, String searchType) {
        this.page = page <= 0 ? 1 : page;
        this.size = size <= 0 ? 10 : size;
        this.keyword = keyword == null ? "" : keyword;
        this.searchType = searchType == null ? "" : searchType;
    }

    public int getOffset() {
        return (page - 1) * size;
    }
}
