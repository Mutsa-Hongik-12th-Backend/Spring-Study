package hello.yunakang.domain;
import java.time.LocalDateTime;

public class Post {
  private int id;
  private String title;
  private LocalDateTime createdAt;
  private String content;

  public Post(int id, String title, LocalDateTime createdAt, String content) {
    this.id = id;
    this.title = title;
    this.createdAt = createdAt;
    this.content = content;
  }

  // Getters and Setters
  public int getId() { return id; }
  public void setId(int id) { this.id = id; }

  public String getTitle() { return title; }
  public void setTitle(String title) { this.title = title; }

  public LocalDateTime getCreatedAt() { return createdAt; }
  public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

  public String getContent() { return content; }
  public void setContent(String content) { this.content = content; }
}
