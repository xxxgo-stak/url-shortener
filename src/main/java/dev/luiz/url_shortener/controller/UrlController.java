package dev.luiz.url_shortener.controller;

import dev.luiz.url_shortener.dto.UrlRequest;
import dev.luiz.url_shortener.dto.UrlResponse;
import dev.luiz.url_shortener.entity.Url;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import dev.luiz.url_shortener.service.UrlService;

import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/api/urls")


public class UrlController {
    private final UrlService urlService;

    public UrlController(UrlService urlService){

        this.urlService = urlService;
    }

    @PostMapping
    public ResponseEntity<UrlResponse> shortenUrl(@Valid @RequestBody UrlRequest request) {
        String code = urlService.shortenUrl(request.getUrl());

        UrlResponse response = new UrlResponse();
        response.setCode(code);
        response.setShortUrl("http://localhost:8080/api/urls/" + code);
        return ResponseEntity.status(201).body(response);
    }

    @GetMapping("/{code}")
    public ResponseEntity<Void> redirect(@PathVariable String code) {
        String originalUrl = urlService.getOriginalUrl(code);
        return ResponseEntity.status(302)
                .header("Location", originalUrl)
                .build();
    }

    @GetMapping("/{code}/stats")
    public Map<String, Object> getStats(@PathVariable String code) {
        Url url = urlService.getUrlStats(code);
        Map<String, Object> stats = new HashMap<>();
        stats.put("code", url.getCode());
        stats.put("originalUrl", url.getOriginalUrl());
        stats.put("clickCount", url.getClickCount());
        stats.put("createdAt", url.getCreatedAt());
        stats.put("expiresAt", url.getExpiresAt());
        return stats;
    }
}
