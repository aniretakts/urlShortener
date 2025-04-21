package com.example.urlshortener.repository;


import com.example.urlshortener.model.UrlMapping;

import java.util.Optional;

public interface UrlMappingRepository {
    Optional<UrlMapping> findByShortCode(String shortCode);
}