package com.jinwoo.dev.pharmacyrecommendation.direction.service;

import com.jinwoo.dev.pharmacyrecommendation.api.dto.KakaoApiResponseDto;
import com.jinwoo.dev.pharmacyrecommendation.api.service.KakaoCategorySearchService;
import com.jinwoo.dev.pharmacyrecommendation.direction.entity.Direction;
import com.jinwoo.dev.pharmacyrecommendation.direction.repository.DirectionRepository;
import com.jinwoo.dev.pharmacyrecommendation.pharmacy.service.PharmacySearchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class DirectionService {

    private static final int MAX_SEARCH_COUNT = 3; //약국 최대 검색 갯수
    private static final double RADIUS_KM = 10.0; //반경 10km
    private static final String DIRECTION_BASE_URL = "https://map.kakao.com/link/map/";

    private final PharmacySearchService pharmacySearchService;
    private final DirectionRepository directionRepository;
    private final KakaoCategorySearchService kakaoCategorySearchService;
    private final Base62Service base62Service;

    @Transactional
    public List<Direction> saveAll(List<Direction> directionList){
        if(CollectionUtils.isEmpty(directionList)) return Collections.emptyList();

        return directionRepository.saveAll(directionList);
    }

    public String findByDirectionUrl(String encodeId){
        Long decodeDirectionId = base62Service.decodeDirectionId(encodeId);

        Direction direction = directionRepository.findById(decodeDirectionId).orElse(null);

        String param = String.join(",", direction.getTargetPharmacyName(), String.valueOf(direction.getTargetLatitude()), String.valueOf(direction.getTargetLongitude()));
        return UriComponentsBuilder.fromHttpUrl(DIRECTION_BASE_URL + param)
                .toUriString();
    }

    public List<Direction> buildDirectionList(KakaoApiResponseDto.DocumentDto documentDto){
        if (Objects.isNull(documentDto)) return Collections.emptyList();

        //약국 데이터 조회
        //거리계산 알고리즘(Haversine formula)을 이용하여, 거리를 계산하여 sort
        return pharmacySearchService.searchPharmacyDtoList().stream()
                .map(pharmacyDto -> Direction.builder()
                        .inputAddress(documentDto.getAddressName())
                        .inputLatitude(documentDto.getLatitude())
                        .inputLongitude(documentDto.getLongitude())
                        .targetPharmacyName(pharmacyDto.pharmacyName())
                        .targetAddress(pharmacyDto.pharmacyAddress())
                        .targetLatitude(pharmacyDto.latitude())
                        .targetLongitude(pharmacyDto.longitude())
                        .distance(calculateDistance(documentDto.getLatitude(), documentDto.getLongitude(), pharmacyDto.latitude(), pharmacyDto.longitude()))
                        .build()
                )
                .filter(direction -> direction.getDistance() <= RADIUS_KM)
                .sorted(Comparator.comparing(Direction::getDistance))
                .limit(MAX_SEARCH_COUNT)
                .collect(Collectors.toList());
    }

    public List<Direction> buildDirectionKakaoCategoryList(KakaoApiResponseDto.DocumentDto documentDto){
        if(Objects.isNull(documentDto)) return Collections.emptyList();

        return kakaoCategorySearchService.requestPharmacyCategorySearch(documentDto.getLatitude(), documentDto.getLongitude(), RADIUS_KM)
                .getDocumentList().stream()
                .map(document -> Direction.builder()
                        .inputAddress(documentDto.getAddressName())
                        .inputLatitude(documentDto.getLatitude())
                        .inputLongitude(documentDto.getLongitude())
                        .targetPharmacyName(document.getPlaceName())
                        .targetAddress(document.getAddressName())
                        .targetLatitude(document.getLatitude())
                        .targetLongitude(document.getLongitude())
                        .distance(calculateDistance(documentDto.getLatitude(), documentDto.getLongitude(), document.getLatitude(), document.getLongitude()))
                        .build()
                )
                .limit(MAX_SEARCH_COUNT)
                .collect(Collectors.toList());
    }

    // Haversine formula
    private double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        lat1 = Math.toRadians(lat1);
        lon1 = Math.toRadians(lon1);
        lat2 = Math.toRadians(lat2);
        lon2 = Math.toRadians(lon2);

        double earthRadius = 6371; //Kilometers
        return earthRadius * Math.acos(Math.sin(lat1) * Math.sin(lat2) + Math.cos(lat1) * Math.cos(lat2) * Math.cos(lon1 - lon2));
    }
}
