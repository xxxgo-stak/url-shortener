package dev.luiz.url_shortener.service;

import dev.luiz.url_shortener.entity.Url;
import dev.luiz.url_shortener.exception.UrlNotFoundException;
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
        url.setExpiresAt(LocalDateTime.now().plusDays(7));
        urlRepository.save(url);
        return code;
    }

    public String getOriginalUrl(String code){
        Optional<Url> url = urlRepository.findByCode(code);
        if (url.isPresent()) {
            if (url.get().getExpiresAt() != null && url.get().getExpiresAt().isBefore(LocalDateTime.now()))
                throw new UrlNotFoundException("Url expirada");
            return url.get().getOriginalUrl();
        } else {
            throw new UrlNotFoundException("URL não encontrada");
        }
    }
}
