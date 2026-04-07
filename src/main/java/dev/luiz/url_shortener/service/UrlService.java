package dev.luiz.url_shortener.service;

import dev.luiz.url_shortener.entity.Url;
import dev.luiz.url_shortener.exception.UrlNotFoundException;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import dev.luiz.url_shortener.repository.UrlRepository;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class UrlService {
    private final UrlRepository urlRepository;

    public UrlService(UrlRepository urlRepository) {
        this.urlRepository = urlRepository;
    }

    public String shortenUrl(String originalUrl) {
        String code = UUID.randomUUID().toString().substring(0, 8);
        Url url = new Url();
        url.setCode(code);
        url.setOriginalUrl(originalUrl);
        url.setCreatedAt(LocalDateTime.now());
        url.setExpiresAt(LocalDateTime.now().plusDays(7));
        urlRepository.save(url);
        return code;
    }
    @Cacheable(value = "urls", key = "#code")
    public String findOriginalUrl(String code) {
        return urlRepository.findByCode(code)
                .orElseThrow(() -> new UrlNotFoundException("Url não encontrada"))
                .getOriginalUrl();
    }

    public String getOriginalUrl(String code) {
        Url urlEntity = urlRepository.findByCode(code)
                .orElseThrow(() -> new UrlNotFoundException("URL não encontrada"));

        if (urlEntity.getExpiresAt() != null && urlEntity.getExpiresAt().isBefore(LocalDateTime.now())) {
            throw new UrlNotFoundException("URL expirada");
        }

        urlEntity.setClickCount(urlEntity.getClickCount() + 1);
        urlRepository.save(urlEntity);
        return findOriginalUrl(code);
    }

    public Url getUrlStats(String code) {
        return urlRepository.findByCode(code)
                .orElseThrow(() -> new UrlNotFoundException("URL não encontrada"));
    }
}