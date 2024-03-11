package com.jinwoo.dev.pharmacyrecommendation.direction.entity;

import com.jinwoo.dev.pharmacyrecommendation.BaseTimeEntity;
import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "direction")
public class Direction extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 사용자가 입력한 정보
    private String inputAddress;
    private double inputLatitude;
    private double inputLongitude;

    // 약국 정보
    private String targetPharmacyName;
    private String targetAddress;
    private double targetLatitude;
    private double targetLongitude;

    // 사용자 입력 위치와 약국 위치간 거리
    private double distance;
}


