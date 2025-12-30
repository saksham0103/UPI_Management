package com.tcs.dto;

public class CountResponseDto {

    private Long count;

    public CountResponseDto(Long count) {
        this.count = count;
    }

    public long getCount() {
        return count;
    }
}
