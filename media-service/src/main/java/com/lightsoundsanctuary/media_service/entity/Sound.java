package com.lightsoundsanctuary.media_service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "sounds")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Sound {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    // Kept as s3Url for existing API/database compatibility.
    // In local mode, this stores a local file URL such as /api/media/uploads/sounds/file.mp3.
    // In S3 mode, this stores the real S3 URL.
    private String s3Url;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    // Optional: public/private status, user ID, timestamp, etc.
}
