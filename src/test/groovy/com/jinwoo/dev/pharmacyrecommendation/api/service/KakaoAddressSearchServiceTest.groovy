package com.jinwoo.dev.pharmacyrecommendation.api.service

import com.jinwoo.dev.pharmacyrecommendation.AbstractIntegrationContainerBaseTest
import com.jinwoo.dev.pharmacyrecommendation.api.dto.KakaoApiResponseDto
import org.springframework.beans.factory.annotation.Autowired

class KakaoAddressSearchServiceTest extends AbstractIntegrationContainerBaseTest{

    @Autowired
    private KakaoAddressSearchService addressSearchService

    def "address 파라미터 값이 null이면, requestAddressSearch 메소드는 null을 리턴한다." (){
        given:
        String address

        when:
        def result = addressSearchService.requestAddressSearch(address)

        then:
        result == null
    }

    def "address 파라미터 값이 vaild하면, requestAddressSearch 메소드가 정상적으로 값을 리턴한다." (){
        given:
        String address = "서울 성북구 종암로 10길"

        when:
        def result = addressSearchService.requestAddressSearch(address)

        then:
        result.documentList.size() > 0
        result.metaDto.totalCount > 0
        result.documentList.get(0).addressName != null
    }
}
