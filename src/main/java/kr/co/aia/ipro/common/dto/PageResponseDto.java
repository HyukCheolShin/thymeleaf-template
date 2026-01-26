package kr.co.aia.ipro.common.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class PageResponseDto<T> {
    private List<T> list;
    private int totalItems;
    private int totalPages;
    private int currentPage;

    public PageResponseDto(List<T> list, int totalItems, int size, int currentPage) {
        this.list = list;
        this.totalItems = totalItems;
        this.currentPage = currentPage;
        this.totalPages = (int) Math.ceil((double) totalItems / size);
    }
}
