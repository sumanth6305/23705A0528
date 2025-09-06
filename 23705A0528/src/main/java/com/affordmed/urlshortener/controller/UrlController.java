package com.affordmed.urlshortener.controller;

import com.affordmed.urlshortener.service.UrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/shorturls")
public class UrlController {

    @Autowired
    private UrlService urlService;

    @PostMapping
    public ResponseEntity<Map<String, Object>> createShortUrl(@RequestBody Map<String, Object> request) {
        String url = (String) request.get("url");
        Integer validity = request.containsKey("validity") ? (Integer) request.get("validity") : null;
        String shortCode = (String) request.get("shortcode");
        Map<String, Object> response = urlService.createShortUrl(url, validity, shortCode);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{shortCode}")
    public Map<String, Object> getStatistics(@PathVariable String shortCode) {
        return urlService.getStatistics(shortCode);
    }
}