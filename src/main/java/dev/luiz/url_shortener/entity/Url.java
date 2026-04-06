package dev.luiz.url_shortener.entity;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "urls")

public class Url {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String code;
    private  String originalUrl;
    private LocalDateTime createdAt;
    private LocalDateTime expiresAt;
    private Long clickCount = 0L;

    public Url() {
    }

   //getters e setters ----------------------------------

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }

    public String getOriginalUrl() {
        return originalUrl;
    }
    public void setOriginalUrl(String originalUrl) {
        this.originalUrl = originalUrl;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getExpiresAt() {return expiresAt;}
    public void setExpiresAt(LocalDateTime expiresAt) {this.expiresAt = expiresAt; }

    public Long getClickCount() {return clickCount;}
    public void setClickCount(Long clickCount) {this.clickCount = clickCount; }
}
