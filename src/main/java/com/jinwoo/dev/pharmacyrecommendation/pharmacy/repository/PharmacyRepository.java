package com.jinwoo.dev.pharmacyrecommendation.pharmacy.repository;

import com.jinwoo.dev.pharmacyrecommendation.pharmacy.entity.Pharmacy;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PharmacyRepository extends JpaRepository<Pharmacy, Long> {
}
