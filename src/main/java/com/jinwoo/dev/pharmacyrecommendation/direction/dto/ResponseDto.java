package com.jinwoo.dev.pharmacyrecommendation.direction.dto;

import com.jinwoo.dev.pharmacyrecommendation.direction.entity.Direction;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.GetMapping;

@Getter
@Setter
@Builder
public class ResponseDto {
    private String pharmacyName;    // 약국 명
    private String pharmacyAddress; // 약국 주소
    private String directionUrl;    // 길안내 url
    private String roadViewUrl;     // 로드뷰 url
    private String distance;        // 고객 주소와 약국 주소의 거리

    public static ResponseDto from(Direction direction){
        return new ResponseDto(
                direction.getTargetPharmacyName()
                , direction.getTargetAddress()
                , null
                , null
                , String.format("%.2f km", direction.getDistance())
        );
    }
}
