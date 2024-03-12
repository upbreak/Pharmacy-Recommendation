package com.jinwoo.dev.pharmacyrecommendation.direction.dto;

import com.jinwoo.dev.pharmacyrecommendation.direction.entity.Direction;
import com.jinwoo.dev.pharmacyrecommendation.direction.service.Base62Service;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.util.UriComponentsBuilder;

@Getter
@Setter
@Builder
public class ResponseDto {

    private String pharmacyName;    // 약국 명
    private String pharmacyAddress; // 약국 주소
    private String directionUrl;    // 길안내 url
    private String roadViewUrl;     // 로드뷰 url
    private String distance;        // 고객 주소와 약국 주소의 거리

}
