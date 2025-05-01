package com.it.telescopeplatform.telescope.models;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import com.it.telescopeplatform.image.models.Image;
import com.it.telescopeplatform.publisher.models.Publisher;
import com.it.telescopeplatform.review.models.Review;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "Telescopes")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Telescope {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String description;

    private String brand;

    private String type;

    private BigDecimal aperture;

    private BigDecimal pricePerDay;

    private Boolean available = true;

    @OneToMany(mappedBy = "telescope", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Image> images;

    @OneToMany(mappedBy = "telescope", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Review> reviews;

    @ManyToOne
    @JoinColumn(name = "publisher_id")
    private Publisher publisher;

    @Column(updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();
}