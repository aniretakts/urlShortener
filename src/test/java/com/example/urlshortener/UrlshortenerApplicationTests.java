package com.example.urlshortener;

import com.example.urlshortener.exception.UrlNotFoundException;
import com.example.urlshortener.service.UrlShortenerService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UrlshortenerApplicationTests {

	@Autowired
	private UrlShortenerService service;

    @Test
    void testShortenAndRetrieveUrl() {
        String original = "https://google.com";
        String shortUrl = service.shortenUrl(original, null);
        String fetched = service.getOriginalUrl(shortUrl);
        Assertions.assertEquals(original, fetched);
    }

    @Test
    void testShortenSameUrl() {
        String original = "https://google.com";
        String firstShortUrl = service.shortenUrl(original, null);
        String secondShortUrl = service.shortenUrl(original, null);

        Assertions.assertNotEquals(firstShortUrl, secondShortUrl);
    }

    @Test
    void testInvalidShortUrl() {
        String invalidShortUrl = "invalidUrl";

        Assertions.assertThrows(UrlNotFoundException.class, () -> {
            service.getOriginalUrl(invalidShortUrl);
        });
    }

}
