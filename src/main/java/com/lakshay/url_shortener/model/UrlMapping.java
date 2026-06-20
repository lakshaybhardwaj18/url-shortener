package com.lakshay.url_shortener.model;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
@Entity
@Table(name="url_mappings")
@Data
@NoArgsConstructor
public class UrlMapping {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,length = 2048)
    private String longUrl;

    @Column(nullable = true,length = 10)
    private  String shortCode;

    @Column(nullable = false)
    private  LocalDateTime createdAt;
    private  LocalDateTime expiredAt;
    private  Long hitCount = 0L;

    @PrePersist
    public void prePersist(){
        this.createdAt=LocalDateTime.now();
    }
}
