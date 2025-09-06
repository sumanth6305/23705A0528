package com.affordmed.urlshortener.entity;

import jakarta.persistence.Embeddable;
import lombok.Data;

import java.time.LocalDateTime;

@Embeddable
@Data
public class ClickData {
    private LocalDateTime timestamp;
    private String referrer;
    private String location;  // Coarse-grained, e.g., country from IP
}