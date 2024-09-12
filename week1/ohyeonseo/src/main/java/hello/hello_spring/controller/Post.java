package hello.hello_spring.controller;

import java.time.LocalDateTime;

public record Post(Long id, String title, LocalDateTime createdAt, String content){

}
