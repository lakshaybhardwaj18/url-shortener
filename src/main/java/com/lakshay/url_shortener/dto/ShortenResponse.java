package com.lakshay.url_shortener.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
@Data
@AllArgsConstructor
public class ShortenResponse {
    private String shortUrl;
    private String longUrl;
}
