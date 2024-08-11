package com.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 메인페이지를 제공할 메인컨트롤러
 */

@Controller // 컨트롤러 클래스 명시(컴포넌트 스캔->(빈 객체로 등록되어 애플리케이션 실행시점에 컨텍스트에 업로드됨)에 사용)
public class MainController {
    @GetMapping("/index")
    public String getMainPage(){
        return "index.html"; // resource/static/index.html 반환
    }
}
