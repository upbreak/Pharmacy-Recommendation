package com.jinwoo.dev.pharmacyrecommendation.direction.controller

import com.jinwoo.dev.pharmacyrecommendation.direction.dto.ResponseDto
import com.jinwoo.dev.pharmacyrecommendation.pharmacy.service.PharmacyRecommendationService
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import spock.lang.Specification

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.log
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

class FormControllerTest extends Specification{

    private MockMvc mockMvc
    private PharmacyRecommendationService pharmacyRecommendationService = Mock()
    private List<ResponseDto> responseList

    def setup(){
        mockMvc = MockMvcBuilders.standaloneSetup(new FormController(pharmacyRecommendationService)).build()

        responseList = new ArrayList<>()
        responseList.addAll(
                ResponseDto.builder().pharmacyName("pharmacy1").build()
                , ResponseDto.builder().pharmacyName("pharmacy2").build()
        )
    }

    def "get /" (){
        expect:
        mockMvc.perform (get("/"))
        .andExpect (handler().handlerType(FormController.class))
        .andExpect (handler().methodName("main"))
        .andExpect (status().isOk())
        .andExpect (view().name("main"))
        .andDo  (log())
    }

    def "post /search"(){
        given:
        String address = "서울 성북구 종암동"

        when:
        def action = mockMvc.perform(MockMvcRequestBuilders.post("/search").param("address", address))

        then:
        1 * pharmacyRecommendationService.recommendationPharmacyList(address) >> responseList

        action
                .andExpect(status().isOk())
                .andExpect(view().name("output"))
        .andExpect {model().attributeExists("responseList")}
        .andExpect {model().attribute("responseList", responseList)}
        .andDo {print()}
    }
}
