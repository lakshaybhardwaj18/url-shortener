package com.lakshay.url_shortener.dto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
@Data
public class ShortenRequest{
    @NotBlank(message = "URL must not be blank")
    @Pattern(
            regexp="^(https?://).*",
            message = "URL must start with http:// or https://"
    )
    private String longUrl;

    @Min(value=1, message="TTL must be at least 1 day")
    private Integer ttlDays; // optional, null means never expire
}
