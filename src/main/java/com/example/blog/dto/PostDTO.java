package com.example.blog.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
public class PostDTO {

    private Long id;
    private Long authorId;
    private String slug;
    private String name;
    private String body;
    private LocalDate createdAt;
}