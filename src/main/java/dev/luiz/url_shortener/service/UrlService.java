package dev.luiz.url_shortener.service;

import dev.luiz.url_shortener.entity.Url;
import org.springframework.stereotype.Service;
import dev.luiz.url_shortener.repository.UrlRepository;

import java.time.LocalDateTime;
import java.util.Optional;
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
        urlRepository.save(url);
        return code;
    }

    public String getOriginalUrl(String code){
        Optional<Url> url = urlRepository.findByCode(code);
        if (url.isPresent()) {
            return url.get().getOriginalUrl();
        } else {
            throw new RuntimeException("URL não encontrada");
        }
    }
}
