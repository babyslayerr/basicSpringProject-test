package com.example.controller;

import com.example.service.KakaoPayService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor // 생성자 주입
public class PaymentController {

    private final KakaoPayService kakaoPayService;

    @PostMapping("/api/postReadyKakaoPayment")
    public Map<String, String> postReadyKakaoPayment(HttpServletResponse response) throws IOException {
        String redirect_url = kakaoPayService.readyPayment();
        
        // servlet response 에 카카오에서 받은 url 삽입
        // CORS 위반으로 아래내용은 불가
        // 직접화면단에서 window.location.href 사용
        // response.sendRedirect(redirect_url);
        // response.setStatus(302);
        Map<String, String> returnMap = new HashMap<>();
        returnMap.put("redirect_url", redirect_url);
        return returnMap;
    }
}
