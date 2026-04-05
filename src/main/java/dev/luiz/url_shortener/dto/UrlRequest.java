package dev.luiz.url_shortener.dto;

import jakarta.validation.constraints.NotBlank;

public class UrlRequest {
    @org.hibernate.validator.constraints.URL
    @NotBlank
    private String url;
    public UrlRequest() {}
    public String getUrl() {
        return url;
    }
    public void setUrl(String url){
        this.url = url;
    }
}
