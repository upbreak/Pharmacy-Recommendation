package com.jinwoo.dev.pharmacyrecommendation.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class KakaoApiResponseDto {

    @JsonProperty("meta")
    private MetaDto metaDto;

    @JsonProperty("documents")
    private List<DocumentDto> documentList;

    @Getter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class MetaDto {

        @JsonProperty("total_count")
        private Integer totalCount;
    }

    @Getter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class DocumentDto {

        @JsonProperty("place_name")
        private String placeName;

        @JsonProperty("address_name")
        private String addressName;

        @JsonProperty("x")
        private double longitude;

        @JsonProperty("y")
        private double latitude;

        @JsonProperty("distance")
        private double distance;

    }
}
