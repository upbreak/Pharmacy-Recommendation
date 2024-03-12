package com.jinwoo.dev.pharmacyrecommendation.direction.service


import com.jinwoo.dev.pharmacyrecommendation.api.dto.KakaoApiResponseDto
import com.jinwoo.dev.pharmacyrecommendation.api.service.KakaoCategorySearchService
import com.jinwoo.dev.pharmacyrecommendation.direction.repository.DirectionRepository
import com.jinwoo.dev.pharmacyrecommendation.pharmacy.dto.PharmacyDto
import com.jinwoo.dev.pharmacyrecommendation.pharmacy.service.PharmacySearchService
import spock.lang.Specification

class DirectionServiceTest extends Specification{

    private final PharmacySearchService pharmacySearchService = Mock()
    private final DirectionRepository directionRepository = Mock()
    private final KakaoCategorySearchService kakaoCategorySearchService = Mock()
    private final Base62Service base62Service = Mock()

    private DirectionService directionService = new DirectionService(
                                            pharmacySearchService
                                            , directionRepository
                                            , kakaoCategorySearchService
                                            , base62Service
    )

    private List<PharmacyDto> pharmacyDtos

    def setup(){
        pharmacyDtos = new ArrayList<>()
        pharmacyDtos.addAll(
                PharmacyDto.builder()
                        .id(1L)
                        .pharmacyName("돌곶이온누리약국")
                        .pharmacyAddress("주소1")
                        .latitude(37.61040424)
                        .longitude(127.0569046)
                        .build(),
                PharmacyDto.builder()
                        .id(2L)
                        .pharmacyName("호수온누리약국")
                        .pharmacyAddress("주소2")
                        .latitude(37.60894036)
                        .longitude(127.029052)
                        .build()
        )
    }

    def "buildDirectionList - 결과 값이 거리 순으로 정렬이 되는지 확인"(){
        given:
        def addressName = "서울 성북구 종암로10길"
        double inputLatitude = 37.5960650456809
        double inputLongitude = 127.037033003036

        def documentDto = KakaoApiResponseDto.DocumentDto.builder()
                                                                    .addressName(addressName)
                                                                    .latitude(inputLatitude)
                                                                    .longitude(inputLongitude)
                                                                    .build()

        when:
        pharmacySearchService.searchPharmacyDtoList() >> pharmacyDtos
        def result = directionService.buildDirectionList(documentDto)

        then:
        result.size() == 2
        result.get(0).targetPharmacyName == "호수온누리약국"
        result.get(1).targetPharmacyName == "돌곶이온누리약국"
    }

    def "buildDirectionList - 정해진 반경 10km 내에 검색이 되는지 확인"(){
        given:
        pharmacyDtos.add(
                PharmacyDto.builder()
                        .id(3L)
                        .pharmacyName("경기약국")
                        .pharmacyAddress("주소3")
                        .latitude(37.3825107393401)
                        .longitude(127.236707811313)
                        .build())

        def addressName = "서울 성북구 종암로10길"
        double inputLatitude = 37.5960650456809
        double inputLongitude = 127.037033003036

        def documentDto = KakaoApiResponseDto.DocumentDto.builder()
                .addressName(addressName)
                .latitude(inputLatitude)
                .longitude(inputLongitude)
                .build()

        when:
        pharmacySearchService.searchPharmacyDtoList() >> pharmacyDtos
        def result = directionService.buildDirectionList(documentDto)

        then:
        result.size() == 2
        result.get(0).targetPharmacyName == "호수온누리약국"
        result.get(1).targetPharmacyName == "돌곶이온누리약국"
    }
}
