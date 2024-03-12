package com.jinwoo.dev.pharmacyrecommendation.direction.controller;

import com.jinwoo.dev.pharmacyrecommendation.direction.dto.RequestDto;
import com.jinwoo.dev.pharmacyrecommendation.direction.dto.ResponseDto;
import com.jinwoo.dev.pharmacyrecommendation.pharmacy.service.PharmacyRecommendationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Controller
public class FormController {

    private final PharmacyRecommendationService pharmacyRecommendationService;

    @GetMapping("/")
    public String main(){
        return "main";
    }

    @PostMapping("/search")
    public ModelAndView postDirection(RequestDto dto){
        List<ResponseDto> responseList = pharmacyRecommendationService.recommendationPharmacyList(dto.address());

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("output");
//        modelAndView.addObject("responseList", OutputDto);
        modelAndView.addObject("responseList", responseList);

        return modelAndView;
    }
}
