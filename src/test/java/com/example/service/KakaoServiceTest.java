package com.example.service;

import org.junit.Test;

public class KakaoServiceTest {
    @Test //순수자바코드로 구현했기 때문에 SpringTest 설정x
    public void httpConnectionTest(){
        KakaoPayService kakaoPayService = new KakaoPayService();

        kakaoPayService.testExample();

    }
    @Test
    public void readyPayment(){
        KakaoPayService kakaoPayService = new KakaoPayService();
        kakaoPayService.readyPayment();
    }
}
