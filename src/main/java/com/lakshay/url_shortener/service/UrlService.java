package com.lakshay.url_shortener.service;
import com.lakshay.url_shortener.dto.ShortenRequest;
import com.lakshay.url_shortener.dto.ShortenResponse;
import com.lakshay.url_shortener.dto.StatsResponse;
import com.lakshay.url_shortener.exception.UrlExpiredException;
import com.lakshay.url_shortener.exception.UrlNotFoundException;
import com.lakshay.url_shortener.model.UrlMapping;
import com.lakshay.url_shortener.repository.UrlRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UrlService {
    private final UrlRepository urlRepository;
    private final HashService hashService;
    @Value("${app.base-url}")
    private String baseUrl;
    public ShortenResponse shortenUrl(ShortenRequest request){
        // Step 1 — save entity first to get DB generated ID
        UrlMapping urlMapping=new UrlMapping();
        urlMapping.setLongUrl(request.getLongUrl());
        //set expiry if ttlDays provided
        if(request.getTtlDays()!=null){
            urlMapping.setExpiredAt(LocalDateTime.now().plusDays(request.getTtlDays()));
        }
        UrlMapping saved=urlRepository.save(urlMapping);

        //Step 2- encode the aID into Base62 short code
        String shortCode=hashService.encode(saved.getId());

        //Step 3- update the entity with the short code
        saved.setShortCode(shortCode);
        urlRepository.save(saved);
        //Step 4- build and return the response
        String shortUrl=baseUrl + "/"+shortCode;
        return new ShortenResponse(shortUrl, request.getLongUrl());
    }

    @Cacheable(value = "urls",key = "#shortCode")
    public String getLongUrl(String shortCode){
        UrlMapping urlMapping = urlRepository.findByShortCode(shortCode)
                .orElseThrow(()-> new UrlNotFoundException(shortCode));
        // check expiry
        if(urlMapping.getExpiredAt()!=null &&
                LocalDateTime.now().isAfter(urlMapping.getExpiredAt())){
            throw new UrlExpiredException(shortCode);
        }

        return urlMapping.getLongUrl();
    }

    @Transactional
    public void trackClick(String shortCode){
        urlRepository.incrementHitCount(shortCode);
    }

    public StatsResponse getStats(String shortCode){
        UrlMapping urlMapping= urlRepository.findByShortCode(shortCode)
                .orElseThrow(()-> new UrlNotFoundException(shortCode));
        String shortUrl = baseUrl+ "/" + shortCode;

        return new StatsResponse(
                shortCode,
                shortUrl,
                urlMapping.getLongUrl(),
                urlMapping.getHitCount(),
                urlMapping.getCreatedAt(),
                urlMapping.getExpiredAt()
        );
    }
}
