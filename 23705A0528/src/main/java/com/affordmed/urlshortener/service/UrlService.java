package com.affordmed.urlshortener.service;

import com.affordmed.urlshortener.entity.ClickData;
import com.affordmed.urlshortener.entity.ShortenedUrl;
import com.affordmed.urlshortener.exception.ExpiredLinkException;
import com.affordmed.urlshortener.exception.ResourceNotFoundException;
import com.affordmed.urlshortener.exception.ShortcodeCollisionException;
import com.affordmed.urlshortener.repository.ShortenedUrlRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class UrlService {

    @Autowired
    private ShortenedUrlRepository repository;

    private static final String BASE62 = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final int SHORT_CODE_LENGTH = 6;

    public Map<String, Object> createShortUrl(String url, Integer validityMinutes, String shortCode) {
        validateUrl(url);

        String finalShortCode = shortCode != null ? shortCode : generateUniqueShortCode();
        if (repository.existsByShortCode(finalShortCode)) {
            throw new ShortcodeCollisionException("Shortcode already exists");
        }

        LocalDateTime expiry = LocalDateTime.now().plusMinutes(validityMinutes != null ? validityMinutes : 30);

        ShortenedUrl shortenedUrl = new ShortenedUrl();
        shortenedUrl.setShortCode(finalShortCode);
        shortenedUrl.setOriginalUrl(url);
        shortenedUrl.setExpiryDate(expiry);
        repository.save(shortenedUrl);

        return Map.of(
                "shortLink", "http://localhost:8080/" + finalShortCode,
                "expiry", expiry.toString()  // ISO 8601 format
        );
    }

    public String redirect(String shortCode, String referrer, String ipAddress) {
        ShortenedUrl url = repository.findByShortCode(shortCode)
                .orElseThrow(() -> new ResourceNotFoundException("Shortcode not found"));

        if (url.getExpiryDate().isBefore(LocalDateTime.now())) {
            throw new ExpiredLinkException("Link has expired");
        }

        // Increment click count
        url.setClickCount(url.getClickCount() + 1);

        // Log click data (location mocked for simplicity; in production, use GeoIP)
        ClickData click = new ClickData();
        click.setTimestamp(LocalDateTime.now());
        click.setReferrer(referrer);
        click.setLocation(getLocationFromIp(ipAddress));  // Implement or mock
        url.getClickData().add(click);

        repository.save(url);

        return url.getOriginalUrl();
    }

    public Map<String, Object> getStatistics(String shortCode) {
        ShortenedUrl url = repository.findByShortCode(shortCode)
                .orElseThrow(() -> new ResourceNotFoundException("Shortcode not found"));

        return Map.of(
                "originalUrl", url.getOriginalUrl(),
                "creationDate", url.getExpiryDate().minusMinutes(30).toString(),  // Approximate
                "expiryDate", url.getExpiryDate().toString(),
                "clickCount", url.getClickCount(),
                "clickData", url.getClickData()
        );
    }

    private void validateUrl(String url) {
        try {
            new URL(url);
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException("Invalid URL format");
        }
    }

    private String generateUniqueShortCode() {
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        while (true) {
            for (int i = 0; i < SHORT_CODE_LENGTH; i++) {
                sb.append(BASE62.charAt(random.nextInt(BASE62.length())));
            }
            String code = sb.toString();
            if (!repository.existsByShortCode(code)) {
                return code;
            }
            sb.setLength(0);
        }
    }

    private String getLocationFromIp(String ip) {
        // Mock implementation; in real app, use GeoIP library like MaxMind
        return "Unknown";  // Or integrate a service
    }
}