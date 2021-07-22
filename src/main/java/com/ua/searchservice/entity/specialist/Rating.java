package com.ua.searchservice.entity.specialist;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "ratings")
@Data
public class Rating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rating_id")
    private Long ratingId;

    @Column(nullable = false)
    private Integer rating;

    @ManyToOne
    @JoinColumn(name = "specialist_id")
    private Specialist specialist;
}