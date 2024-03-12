package com.jinwoo.dev.pharmacyrecommendation.pharmacy.controller;

import com.jinwoo.dev.pharmacyrecommendation.pharmacy.cache.PharmacyRedisTemplateService;
import com.jinwoo.dev.pharmacyrecommendation.pharmacy.dto.PharmacyDto;
import com.jinwoo.dev.pharmacyrecommendation.pharmacy.service.PharmacyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Controller
public class PharmacyController {

    private final PharmacyService pharmacyService;
    private final PharmacyRedisTemplateService pharmacyRedisTemplateService;

    // 데이터 초기 셋팅을 위한 임시 메서드
    @GetMapping("/redis/save")
    public String save(){
        List<PharmacyDto> pharmacyDtoList = pharmacyService.findAll().stream()
                .map(PharmacyDto::from)
                .collect(Collectors.toList());

        pharmacyDtoList.forEach(pharmacyRedisTemplateService::save);

        return "success";
    }

}
