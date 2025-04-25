package com.example.urlshortener.service;

import com.example.urlshortener.exception.UrlNotFoundException;
import com.example.urlshortener.model.UrlMapping;
import com.example.urlshortener.repository.UrlMappingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class UrlShortenerService {

    @Autowired
    private UrlMappingRepository repository;

    public String shortenUrl(String originalUrl, Duration ttl) {
        String shortUrl = UUID.randomUUID().toString().substring(0, 6);
        UrlMapping mapping = new UrlMapping();
        mapping.setShortUrl(shortUrl);
        mapping.setOriginalUrl(originalUrl);
        mapping.setCreatedAt(LocalDateTime.now());
        if (ttl != null) {
            mapping.setExpiresAt(LocalDateTime.now().plus(ttl));
        }
        repository.save(mapping);
        return shortUrl;
    }

    public String getOriginalUrl(String shortUrl) {
        UrlMapping mapping = repository.findByShortUrl(shortUrl)
                .orElseThrow(() -> new UrlNotFoundException("Short URL not found: " + shortUrl));

        if (mapping.getExpiresAt() != null && mapping.getExpiresAt().isBefore(LocalDateTime.now())) {
            repository.delete(mapping);
            throw new RuntimeException("URL expired");
        }

        return mapping.getOriginalUrl();
    }

}
