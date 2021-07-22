package com.ua.searchservice.repository;

import com.ua.searchservice.entity.specialist.Rating;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RatingRepository extends CrudRepository<Rating, Long> {
}