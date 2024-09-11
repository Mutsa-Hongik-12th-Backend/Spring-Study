package me.jison.postsexample;

import java.time.LocalDateTime;

public record Post(Long id, String title, LocalDateTime createdAt, String content) {
}
