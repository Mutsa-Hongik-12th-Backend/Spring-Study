package com.mutsa.cheolwoo.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class Post {
    private Long id;
    private String title;
    private LocalDateTime createdAt;
    private String content;
}