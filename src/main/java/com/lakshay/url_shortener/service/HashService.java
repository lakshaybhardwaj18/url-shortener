package com.lakshay.url_shortener.service;
import org.springframework.stereotype.Service;
@Service
public class HashService {
    private static final  String ALPHABET=
            "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final int BASE=62;
    public String encode(long id){
        StringBuilder result=new StringBuilder();
        while(id>0){
            int rem=(int)(id%BASE);
            result.append(ALPHABET.charAt(rem));
            id=id/BASE;
        }
        return result.reverse().toString();
    }
    public long decode(String shortCode){
        long result=0;
        for(char c: shortCode.toCharArray()){
            result= result * BASE + ALPHABET.indexOf(c);
        }
        return result;
    }
}
