package com.lakshay.url_shortener.exception;

import com.lakshay.url_shortener.controller.UrlController;

public class UrlNotFoundException extends RuntimeException {
    public UrlNotFoundException(String shortCode){
        super("No URL found for short code: "+shortCode);
    }
}
