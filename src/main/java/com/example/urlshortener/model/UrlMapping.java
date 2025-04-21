package com.example.urlshortener.model;

import java.time.LocalDateTime;

@Entity
public class UrlMapping {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String shortCode;

    private String originalUrl;

    private LocalDateTime createdAt;

    private LocalDateTime expiresAt;

    // Getters and setters
}