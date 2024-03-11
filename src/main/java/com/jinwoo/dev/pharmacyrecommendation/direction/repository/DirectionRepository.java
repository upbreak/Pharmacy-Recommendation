package com.jinwoo.dev.pharmacyrecommendation.direction.repository;

import com.jinwoo.dev.pharmacyrecommendation.direction.entity.Direction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DirectionRepository extends JpaRepository<Direction, Long> {
}
