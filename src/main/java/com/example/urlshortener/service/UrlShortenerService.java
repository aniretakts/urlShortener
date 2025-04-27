package com.example.urlshortener.service;

import com.example.urlshortener.exception.UrlExpiredException;
import com.example.urlshortener.exception.UrlNotFoundException;
import com.example.urlshortener.model.UrlMapping;
import com.example.urlshortener.repository.UrlMappingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

/**
 * Service class for URL shortening and retrieval.
 */
@Service
public class UrlShortenerService {

    private static final LocalDateTime NO_EXPIRATION = LocalDateTime.MAX;

    @Autowired
    private UrlMappingRepository repository;

    /**
     * Shortens a given URL and stores it in the database.
     *
     * @param originalUrl The original URL to shorten.
     * @param ttl         The time-to-live duration for the shortened URL.
     * @return The shortened URL.
     */
    public String shortenUrl(String originalUrl, Duration ttl) {

        Optional<UrlMapping> existingMapping = repository.findByOriginalUrl(originalUrl);

        if (existingMapping.isPresent()) {
            return handleExistingMapping(existingMapping.get());
        }

        return createAndSaveNewMapping(originalUrl, ttl);
    }

    private String handleExistingMapping(UrlMapping mapping) {
        if (isMappingStillValid(mapping)) {
            return mapping.getShortUrl();
        } else {
            repository.delete(mapping);
            return createAndSaveNewMapping(mapping.getOriginalUrl(), null);
        }
    }

    private boolean isMappingStillValid(UrlMapping mapping) {
        LocalDateTime expiresAt = mapping.getExpiresAt() != null ? mapping.getExpiresAt() : NO_EXPIRATION;
        return expiresAt.isAfter(LocalDateTime.now());
    }

    private String createAndSaveNewMapping(String originalUrl, Duration ttl) {
        String shortUrl = generateUniqueShortUrl();
        UrlMapping mapping = new UrlMapping();
        mapping.setShortUrl(shortUrl);
        mapping.setOriginalUrl(originalUrl);
        mapping.setCreatedAt(LocalDateTime.now());
        mapping.setExpiresAt(ttl != null ? LocalDateTime.now().plus(ttl) : null);
        repository.save(mapping);
        return shortUrl;
    }

    private String generateUniqueShortUrl() {
        String shortUrl;
        do {
            shortUrl = UUID.randomUUID().toString().substring(0, 6);
        } while (repository.findByShortUrl(shortUrl).isPresent());
        return shortUrl;
    }

    public String getOriginalUrl(String shortUrl) {
        UrlMapping mapping = repository.findByShortUrl(shortUrl)
                .orElseThrow(() -> new UrlNotFoundException("Short URL not found: " + shortUrl));

        if (mapping.getExpiresAt() != null && mapping.getExpiresAt().isBefore(LocalDateTime.now())) {
            repository.delete(mapping);
            throw new UrlExpiredException("Short URL expired: " + shortUrl);
        }

        return mapping.getOriginalUrl();
    }

}
