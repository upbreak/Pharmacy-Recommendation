package com.jinwoo.dev.pharmacyrecommendation.pharmacy.service;

import com.jinwoo.dev.pharmacyrecommendation.pharmacy.dto.PharmacyDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class PharmacySearchService {

    private final PharmacyService pharmacyService;

    public List<PharmacyDto> searchPharmacyDtoList(){
        //redis

        //db
        return pharmacyService.findAll().stream()
                .map(PharmacyDto::from)
                .collect(Collectors.toList());
    }
}
