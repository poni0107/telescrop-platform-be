package com.it.telescopeplatform.image.models;

import java.time.LocalDateTime;

import com.it.telescopeplatform.accessory.models.Accessory;
import com.it.telescopeplatform.telescope.models.Telescope;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Images")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String imageUrl;

    @ManyToOne
    @JoinColumn(name = "telescope_id")
    private Telescope telescope;

    @ManyToOne
    @JoinColumn(name = "accessory_id")
    private Accessory accessory;

    @Column(updatable = false)
    private LocalDateTime uploadedAt = LocalDateTime.now();
}
