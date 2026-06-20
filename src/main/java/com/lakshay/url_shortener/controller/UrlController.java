package com.lakshay.url_shortener.controller;
import com.lakshay.url_shortener.dto.ShortenRequest;
import com.lakshay.url_shortener.dto.ShortenResponse;
import com.lakshay.url_shortener.dto.StatsResponse;
import com.lakshay.url_shortener.service.UrlService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.StandardSocketOptions;
import java.net.URI;

@RestController
//@RequestMapping("/api")
@RequiredArgsConstructor
public class UrlController {
    private final UrlService urlService;
    @PostMapping("/api/shorten")
    public ResponseEntity<ShortenResponse> shortenUrl(@Valid @RequestBody ShortenRequest request){
        ShortenResponse response= urlService.shortenUrl(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/r/{shortCode}")
    public ResponseEntity<Void> redirect(@PathVariable String shortCode){
        // ignore static file requests
        if (shortCode.contains(".")) {
            return ResponseEntity.notFound().build();
        }

        String longUrl=urlService.getLongUrl(shortCode);
        urlService.trackClick(shortCode);
        HttpHeaders headers=new HttpHeaders();
        headers.setLocation(URI.create(longUrl));

        return ResponseEntity.status(HttpStatus.FOUND).headers(headers).build();
    }

    @GetMapping("/api/stats/{shortCode}")
    public ResponseEntity<StatsResponse> getStats(@PathVariable String shortCode){
        StatsResponse stats= urlService.getStats(shortCode);

        return ResponseEntity.ok(stats);
    }
}
