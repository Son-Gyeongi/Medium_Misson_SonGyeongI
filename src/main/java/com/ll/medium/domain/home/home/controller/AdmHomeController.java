package com.ll.medium.domain.home.home.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/adm")
public class AdmHomeController {

    // 관리자 홈
    @GetMapping("")
    public String showAdmMain() {
        return "home/home/adm/main";
    }

    // 관리자 정보
    @GetMapping("/home/about")
    public String showAdmAbout() {
        return "home/home/adm/about";
    }
}
