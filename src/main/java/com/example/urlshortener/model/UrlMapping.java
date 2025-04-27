package com.example.urlshortener.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * Entity class representing a URL mapping in the database.
 * This class is used to store the original URL and its corresponding short URL.
 */
@Getter
@Setter
@Entity
public class UrlMapping {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String shortUrl;

    private String originalUrl;

    private LocalDateTime createdAt;

    private LocalDateTime expiresAt;
}