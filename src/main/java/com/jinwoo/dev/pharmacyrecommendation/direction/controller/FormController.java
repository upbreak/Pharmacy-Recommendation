package com.jinwoo.dev.pharmacyrecommendation.direction.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@RequiredArgsConstructor
@Controller
public class FormController {

    @GetMapping("/")
    public String main(){
        return "main";
    }
}
