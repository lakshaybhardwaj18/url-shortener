package com.lakshay.url_shortener.dto;

import lombok.Data;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class StatsResponse {
    private String shortCode;
    private String shortUrl;
    private String longUrl;
    private Long hitCount;
    private LocalDateTime createdAt;
    private LocalDateTime expiresAt;
}
