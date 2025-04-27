package com.example.urlshortener;

import com.example.urlshortener.exception.UrlNotFoundException;
import com.example.urlshortener.service.UrlShortenerService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UrlShortenerApplicationTests {

    @Autowired
    private UrlShortenerService urlShortenerService;

    @Test
    void shortenUrl_WhenUrlIsValid_RetrievesOriginalUrl() {
        String original = "https://google.com";
        String shortUrl = urlShortenerService.shortenUrl(original, null);
        String fetched = urlShortenerService.getOriginalUrl(shortUrl);
        Assertions.assertEquals(original, fetched);
    }

    @Test
    void shortenUrl_WhenShorteningSameUrl_GeneratesSameShortUrls() {
        String original = "https://google.com";
        String firstShortUrl = urlShortenerService.shortenUrl(original, null);
        String secondShortUrl = urlShortenerService.shortenUrl(original, null);

        Assertions.assertEquals(firstShortUrl, secondShortUrl);
    }

    @Test
    void getOriginalUrl_WhenShortUrlIsInvalid_ThrowsUrlNotFoundException() {
        String invalidShortUrl = "invalidUrl";

        Assertions.assertThrows(UrlNotFoundException.class, () -> {
            urlShortenerService.getOriginalUrl(invalidShortUrl);
        });
    }

}
