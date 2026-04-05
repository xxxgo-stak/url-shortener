package dev.luiz.url_shortener.controller;

import dev.luiz.url_shortener.dto.UrlRequest;
import dev.luiz.url_shortener.dto.UrlResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import dev.luiz.url_shortener.service.UrlService;


@RestController
@RequestMapping("/api/urls")


public class UrlController {
    private final UrlService urlService;

    public UrlController(UrlService urlService){

        this.urlService = urlService;
    }

    @PostMapping
    public UrlResponse shortenUrl(@Valid @RequestBody UrlRequest request) {
        String code = urlService.shortenUrl(request.getUrl());

        UrlResponse response = new UrlResponse();
        response.setCode(code);
        response.setShortUrl("http://localhost:8080/api/urls/" + code);
        return response;
    }

    @GetMapping("/{code}")
    public ResponseEntity<Void> redirect(@PathVariable String code) {
        String originalUrl = urlService.getOriginalUrl(code);
        return ResponseEntity.status(302)
                .header("Location", originalUrl)
                .build();
    }
}
