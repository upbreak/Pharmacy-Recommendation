package com.jinwoo.dev.pharmacyrecommendation.api.service

import com.fasterxml.jackson.databind.ObjectMapper
import com.jinwoo.dev.pharmacyrecommendation.AbstractIntegrationContainerBaseTest
import com.jinwoo.dev.pharmacyrecommendation.api.dto.KakaoApiResponseDto
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.spockframework.spring.SpringBean
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.testcontainers.shaded.org.bouncycastle.asn1.cms.MetaData


class KakaoAddressSearchServiceRetryTest extends AbstractIntegrationContainerBaseTest{

    @Autowired
    private KakaoAddressSearchService kakaoAddressSearchService

    @SpringBean
    private KakaoUriBuilderService kakaoUriBuilderService = Mock()

    private MockWebServer mockWebServer

    private ObjectMapper mapper = new ObjectMapper()

    private String inputAddress = "서울 성북구 종암로 10길"

    def setup(){
        mockWebServer = new MockWebServer()
        mockWebServer.start()
        println mockWebServer.port
        println mockWebServer.url("/")
    }

    def cleanup(){
        mockWebServer.shutdown()
    }

    def "requestAddressSearch retry - success"(){
        given:
        def responseDto = new KakaoApiResponseDto(
                new KakaoApiResponseDto.MetaDto(1)
                , Arrays.asList(KakaoApiResponseDto.DocumentDto.builder().addressName(inputAddress).build())
        )
        def uri = mockWebServer.url("/").uri()

        when:
        mockWebServer.enqueue(new MockResponse().setResponseCode(504))
        mockWebServer.enqueue(new MockResponse().setResponseCode(200)
            .addHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .setBody(mapper.writeValueAsString(responseDto))
        )

        def result = kakaoAddressSearchService.requestAddressSearch(inputAddress)

        then:
        2 * kakaoUriBuilderService.builderUriByAddressSearch(inputAddress) >> uri //2 * -> 재처리를 2번한 것을 검증
        result.getDocumentList().size() == 1
        result.getMetaDto().totalCount == 1
        result.getDocumentList().get(0).addressName == "서울 성북구 종암로 10길"
    }

    def "requestAddressSearch retry - fail"(){
        given:
        def uri = mockWebServer.url("/").uri()

        when:
        mockWebServer.enqueue(new MockResponse().setResponseCode(504))
        mockWebServer.enqueue(new MockResponse().setResponseCode(504))

        def result = kakaoAddressSearchService.requestAddressSearch(inputAddress)

        then:
        2 * kakaoUriBuilderService.builderUriByAddressSearch(inputAddress) >> uri //2 * -> 재처리를 2번한 것을 검증
        result == null
    }

}
