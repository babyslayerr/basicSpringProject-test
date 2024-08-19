package com.example.controller;

import com.example.service.KakaoPayService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequiredArgsConstructor // 생성자 주입
public class PaymentController {

    private final KakaoPayService kakaoPayService;

    @PostMapping("/api/postReadyKakaoPayment")
    public Map<String, String> postReadyKakaoPayment(HttpServletResponse response) throws IOException {
        return kakaoPayService.readyPayment();
        
        // servlet response 에 카카오에서 받은 url 삽입
        // CORS 위반으로 아래내용은 불가
        // 직접화면단에서 window.location.href 사용
        // response.sendRedirect(redirect_url);
        // response.setStatus(302);
        // 아래로직도 리턴타입 수정으로 deprecated
        // Map<String, String> returnMap = new HashMap<>();
        // returnMap.put("redirect_url", redirect_url);
        // return returnMap;
    }
    /**
     *  카카오페이 결제 요청 후 사용자가 결제화면에서 비밀번호 까지 입력시, approval_url 로 pg_token(성공토큰)을 파라미터로 해서 보냄
     *  그 성공 요청을 받기위한 API
     *  최종적으로 승인 요청 API 실행
     */
    @GetMapping("/api/postReadyKakaoPayment")
    public String getReadyKakaPayment(String pg_token, HttpServletRequest httpServletRequest){

        String tid = null; // 쿠키에서 찾을 결제번호
        // 쿠키
        Cookie[] cookies = httpServletRequest.getCookies();
        // 쿠키에서 tid라는 key를 가진 쿠키를 찾아서 tid에 set
        if(cookies != null){
            for(Cookie cookie : cookies){
                if("tid".equals(cookie.getName())){
                    tid = cookie.getValue();
                    break; // tid를 찾은후 루프 종료
                }
            }
        }
        // 카카오 승인요청 API 호출
        String responseBody = kakaoPayService.approvePayment(pg_token, tid);

        return responseBody;
    }
}
