package com.getyourguide.demo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaginatedResponse<T> {
    private String next;
    private String prev;
    private List<T> results;
    private long total;
    private int totalPages;
    private int pageSize;
}