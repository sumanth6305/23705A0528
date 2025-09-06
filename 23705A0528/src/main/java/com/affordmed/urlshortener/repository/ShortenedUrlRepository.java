package com.affordmed.urlshortener.repository;

import com.affordmed.urlshortener.entity.ShortenedUrl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ShortenedUrlRepository extends JpaRepository<ShortenedUrl, Long> {
    Optional<ShortenedUrl> findByShortCode(String shortCode);
    boolean existsByShortCode(String shortCode);
}