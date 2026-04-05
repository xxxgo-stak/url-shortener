package dev.luiz.url_shortener.dto;

public class UrlResponse {
    private String code;
    private String shortUrl;

    public UrlResponse() {}

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getShortUrl() {
        return shortUrl;
    }

    public void setShortUrl(String shortUrl) {
        this.shortUrl = shortUrl;;
    }

}
