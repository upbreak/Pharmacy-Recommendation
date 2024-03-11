package com.jinwoo.dev.pharmacyrecommendation.pharmacy.dto;

import com.jinwoo.dev.pharmacyrecommendation.pharmacy.entity.Pharmacy;
import lombok.Builder;

@Builder
public record PharmacyDto(
        Long id
        , String pharmacyName
        , String pharmacyAddress
        , double latitude
        , double longitude
) {
    public static PharmacyDto from(Pharmacy pharmacy) {
        return new PharmacyDto(
                pharmacy.getId()
                , pharmacy.getPharmacyName()
                , pharmacy.getPharmacyAddress()
                , pharmacy.getLatitude()
                , pharmacy.getLongitude()
        );
    }
}
