package com.lakshay.url_shortener;
import com.lakshay.url_shortener.service.HashService;
import org.junit.jupiter.api.Test;
import static  org.junit.jupiter.api.Assertions.*;
public class HashServiceTest {
    private final HashService hashService= new HashService();
    @Test
    void testEncode(){
        assertEquals("1",hashService.encode(1));
        assertEquals("a",hashService.encode(10));
        assertEquals("10",hashService.encode(62));
        System.out.println("encode(100000) = " + hashService.encode(100000));
        System.out.println("encode(999999) = " + hashService.encode(999999));
    }
    @Test
    void testDecode(){
        assertEquals(1,hashService.decode("1"));
        assertEquals(10,hashService.decode("a"));
        assertEquals(62,hashService.decode("10"));
    }
    @Test
    void testRoundTrip(){
        long[] ids={1,62,100,999,100000,999999};
        for(long id:ids){
            String encoded= hashService.encode(id);
            long decoded=hashService.decode(encoded);
            System.out.println(id + " → " + encoded + " → " + decoded);
            assertEquals(id, decoded);
        }
    }
}

