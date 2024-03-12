package com.jinwoo.dev.pharmacyrecommendation.pharmacy.service;

import com.jinwoo.dev.pharmacyrecommendation.api.dto.KakaoApiResponseDto;
import com.jinwoo.dev.pharmacyrecommendation.api.service.KakaoAddressSearchService;
import com.jinwoo.dev.pharmacyrecommendation.direction.dto.ResponseDto;
import com.jinwoo.dev.pharmacyrecommendation.direction.entity.Direction;
import com.jinwoo.dev.pharmacyrecommendation.direction.service.Base62Service;
import com.jinwoo.dev.pharmacyrecommendation.direction.service.DirectionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class PharmacyRecommendationService {

    private final KakaoAddressSearchService kakaoAddressSearchService;
    private final DirectionService directionService;
    private final Base62Service base62Service;

    private static final String ROAD_VIEW_BASE_URL = "https://map.kakao.com/link/roadview/";

    @Value("${pharmacy.recommendation.base.url}")
    private String baseUrl;

    public List<ResponseDto> recommendationPharmacyList(String address){
        KakaoApiResponseDto kakaoApiResponseDto = kakaoAddressSearchService.requestAddressSearch(address);

        if(Objects.isNull(kakaoApiResponseDto) || CollectionUtils.isEmpty(kakaoApiResponseDto.getDocumentList())){
            log.error("[PharmacyRecommendationService recommendationPharmacyList] Input address: {}", address);
            return Collections.emptyList();
        }

        KakaoApiResponseDto.DocumentDto documentDto = kakaoApiResponseDto.getDocumentList().get(0);

        //공공기관 약국위치 api
        List<Direction> directionList = directionService.buildDirectionList(documentDto);
        //kakao 약국위치 api
//        List<Direction> directionList = directionService.buildDirectionKakaoCategoryList(documentDto);

        return directionService.saveAll(directionList).stream()
                .map(direction -> ResponseDto.builder()
                        .pharmacyName(direction.getTargetPharmacyName())
                        .pharmacyAddress(direction.getTargetAddress())
                        .directionUrl(baseUrl + base62Service.encodeDirectionId(direction.getId()))
                        .roadViewUrl(ROAD_VIEW_BASE_URL + direction.getTargetLatitude() + "," + direction.getTargetLongitude())
                        .distance(String.format("%.2f km", direction.getDistance()))
                        .build())
                .collect(Collectors.toList());

    }
}
