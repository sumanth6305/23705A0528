package com.affordmed.urlshortener.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
public class ShortenedUrl {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String shortCode;

    @Column(nullable = false)
    private String originalUrl;

    private LocalDateTime expiryDate;

    private Integer clickCount = 0;

    @ElementCollection
    private List<ClickData> clickData = new ArrayList<>();
}