package com.jinwoo.dev.pharmacyrecommendation.pharmacy.service;

import com.jinwoo.dev.pharmacyrecommendation.pharmacy.cache.PharmacyRedisTemplateService;
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
    private final PharmacyRedisTemplateService pharmacyRedisTemplateService;

    public List<PharmacyDto> searchPharmacyDtoList(){
        //redis
        List<PharmacyDto> pharmacyList = pharmacyRedisTemplateService.findAll();
        if(!pharmacyList.isEmpty()) return pharmacyList;

        //db
        return pharmacyService.findAll().stream()
                .map(PharmacyDto::from)
                .collect(Collectors.toList());
    }
}
