package com.jinwoo.dev.pharmacyrecommendation.direction.controller;

import com.jinwoo.dev.pharmacyrecommendation.direction.entity.Direction;
import com.jinwoo.dev.pharmacyrecommendation.direction.service.DirectionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.util.UriComponentsBuilder;

@Slf4j
@RequiredArgsConstructor
@Controller
public class DirectionController {

    private final DirectionService directionService;

    @GetMapping("/dir/{encodeId}")
    public String searchDirection(@PathVariable("encodeId") String encodeId){

        return "redirect:" + directionService.findByDirectionUrl(encodeId);
    }
}
