package com.jinwoo.dev.pharmacyrecommendation.direction.dto;

import com.jinwoo.dev.pharmacyrecommendation.direction.entity.Direction;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.util.UriComponentsBuilder;

@Getter
@Setter
@Builder
public class ResponseDto {
    private static final String ROAD_VIEW_VASE_URL = "https://map.kakao.com/link/roadview/";
    private static final String DIRECTION_BASE_URL = "https://map.kakao.com/link/map/";

    private String pharmacyName;    // 약국 명
    private String pharmacyAddress; // 약국 주소
    private String directionUrl;    // 길안내 url
    private String roadViewUrl;     // 로드뷰 url
    private String distance;        // 고객 주소와 약국 주소의 거리

    public static ResponseDto from(Direction direction){
        String param = String.join(",", direction.getTargetPharmacyName(), String.valueOf(direction.getTargetLatitude()), String.valueOf(direction.getTargetLongitude()));
        String directionUrl = UriComponentsBuilder.fromHttpUrl(DIRECTION_BASE_URL + param)
                .toUriString();

        return new ResponseDto(
                direction.getTargetPharmacyName()
                , direction.getTargetAddress()
                , directionUrl
                , ROAD_VIEW_VASE_URL + direction.getTargetLatitude() + "," + direction.getTargetLongitude()
                , String.format("%.2f km", direction.getDistance())
        );
    }
}
