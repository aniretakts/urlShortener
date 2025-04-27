package com.example.urlshortener.controller;

import com.example.urlshortener.service.UrlShortenerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.Duration;

/**
 * Controller for handling URL shortening and redirection.
 */
@RestController
public class UrlShortenerController {

    @Autowired
    private UrlShortenerService urlShortenerService;

    @PostMapping("/shorten")
    public ResponseEntity<String> shorten(@RequestParam String url,
                                          @RequestParam(required = false) Long ttlSeconds) {

        if (!url.startsWith("http://") && !url.startsWith("https://")) {
            throw new IllegalArgumentException("Invalid URL format");
        }

        Duration ttl = (ttlSeconds != null) ? Duration.ofSeconds(ttlSeconds) : null;
        String shortUrl = urlShortenerService.shortenUrl(url, ttl);
        return ResponseEntity.ok("http://localhost:8080/" + shortUrl);
    }

    @GetMapping("/{shortUrl}")
    public ResponseEntity<Void> redirect(@PathVariable String shortUrl) {
        String originalUrl = urlShortenerService.getOriginalUrl(shortUrl);
        return ResponseEntity.status(HttpStatus.FOUND).location(URI.create(originalUrl)).build();
    }
}
