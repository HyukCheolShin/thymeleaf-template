package com.example.template.common.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class PageResponse<T> {
    private List<T> list;
    private int totalItems;
    private int totalPages;
    private int currentPage;

    public PageResponse(List<T> list, int totalItems, int size, int currentPage) {
        this.list = list;
        this.totalItems = totalItems;
        this.currentPage = currentPage;
        this.totalPages = (int) Math.ceil((double) totalItems / size);
    }
}
