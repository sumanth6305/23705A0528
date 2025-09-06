package com.affordmed.urlshortener.controller;

import com.affordmed.urlshortener.service.UrlService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

@RestController
public class RedirectController {

    @Autowired
    private UrlService urlService;

    @GetMapping("/{shortCode}")
    public RedirectView redirect(@PathVariable String shortCode, HttpServletRequest request) {
        String referrer = request.getHeader("Referer");
        String ip = request.getRemoteAddr();
        String originalUrl = urlService.redirect(shortCode, referrer, ip);
        return new RedirectView(originalUrl);
    }
}