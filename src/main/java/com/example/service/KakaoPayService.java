package com.example.service;

import org.springframework.stereotype.Service;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/*
* 사전 사항
* 1. 카카오 페이 애플리케이션 페이지에서 프로젝트 등록(시크릿 키 사용)
* 2. 카카오 페이 애플리케이션 플랫폼에서 애플리케이션 서버정보 등록
* */
@Service
public class KakaoPayService {
    /**
     * Http통신 테스트
     */
    public void testExample(){
        String urlString = "https://jsonplaceholder.typicode.com/posts/1"; // 요청할 HTTPS URL -> 현재는 fake API 사용
        HttpURLConnection connection = null;  //http 단일요청을 위한 인스턴스

        try {
            // URL 객체 생성
            URL url = new URL(urlString);
            // HttpURLConnection 객체 생성
            connection = (HttpURLConnection) url.openConnection();
            // 요청 방식 설정 (GET)
            connection.setRequestMethod("GET");
            // 연결 타임아웃 설정(5초)
            connection.setConnectTimeout(5000);
            // 읽기 타임아웃 설정
            connection.setReadTimeout(5000);

            // 응답 코드 확인(요청 시점)
            int responseCode = connection.getResponseCode();
            System.out.println("Response Code: " + responseCode);

            // 응답을 읽기 위한 BufferedReader 생성
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            // 응답 내용
            StringBuilder response = new StringBuilder();

            // 응답 내용 읽기
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            // stream 종료
            in.close();

            // 응답 내용 출력
            System.out.println("Response Body: " + response.toString());

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect(); // 연결 종료
            }
        }
    }

    /**
     * 카카오 결제 준비 메소드
     */
    public String readyPayment(){
        // 요청할 url준비
        String urlString = "https://open-api.kakaopay.com/online/v1/payment/ready";
        // http 요청객체 초기화
        HttpURLConnection httpURLConnection = null;
        // return객체 초기화
        String redirect_pc_url = null;

        try {
            //java.net 패키지의 url클래스 호출
            URL url = new URL(urlString);
            // Casting처리
            httpURLConnection = (HttpURLConnection) url.openConnection();
            // 메소드 SET
            httpURLConnection.setRequestMethod("POST");
            // 연결 TIME OUT(5초)
            httpURLConnection.setConnectTimeout(5000);
            // 읽기 TIME OUT(5초)
            httpURLConnection.setReadTimeout(5000);

            // header값 set
            httpURLConnection.setRequestProperty("Host","open-api.kakaopay.com");
            // TEST SECRET KEY
            httpURLConnection.setRequestProperty("Authorization","SECRET_KEY DEV3220D75D6D2A96CD9978BA77D0C21B0A360C9");
            httpURLConnection.setRequestProperty("Content-Type", "application/json");

            // post요청의 바디에 담아보낼 데이터jsonBody
            // TEST : Body(아래는 필수값들이며 추가적인 파라미터는 사이트 참고)
            String body = "{\"cid\" : \"TC0ONETIME\"," // TEST 가맹 ID
                    + "\"partner_order_id\":\"1\"," // 가맹 주문 ID
                    + "\"partner_user_id\":\"testUser\"," // 가맹 고객 회원 ID
                    + "\"item_name\":\"testItem\"," // 상품명
                    + "\"quantity\":200," // 상품량
                    + "\"total_amount\":200000," // 총 결제금액
                    + "\"tax_free_amount\":20000," // 공제금액
                    + "\"approval_url\":\"http://localhost:8080/\"," // 결제 성공 redirect_url
                    + "\"cancel_url\":\"http://localhost:8080/\"," // 결제 취소 redirect_url
                    + "\"fail_url\":\"http://localhost:8080/\"" // 결제 실패 redirect_url
                    + "}";
            // Post요청을 보내기 위한 출력스트림 사용
            httpURLConnection.setDoOutput(true);
            // body를 담을 outputStream
            OutputStream bodyOutPutStream = httpURLConnection.getOutputStream();
            // body를 UTF-8로 인코딩 된 바이너리를 출력한다
            bodyOutPutStream.write(body.getBytes("UTF-8"));
            // 아웃스트림 닫기
            bodyOutPutStream.close();

            // 상태코드(실행시점)
            int responseCode = httpURLConnection.getResponseCode();
            // 응답라인
            String inputLine;
            // 스트림
            InputStream in= null;
            if (responseCode == HttpURLConnection.HTTP_OK){
                // 정상적인 상태시 스트림
                in = httpURLConnection.getInputStream();
            }else{
                // 에러발생시 스트림
                in = httpURLConnection.getErrorStream();
            }

            System.out.println("Response : " + responseCode);
            // 응답을 읽기 위한 BufferedReader 생성
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            // 응답 내용
            StringBuilder sb = new StringBuilder();
            // 응답 내용 읽기
            while((inputLine = reader.readLine()) != null){
                sb.append(inputLine);
            }
            // 스트림 닫기
            reader.close();
            // 응답 내용 출력
            System.out.println("Response : " + sb.toString());

            // redirect될 url
            redirect_pc_url = getVersion(sb.toString(), "next_redirect_pc_url");


        } catch (IOException e) {
            // 스택추적 출력
            e.printStackTrace();
        } finally {
            if(httpURLConnection != null){
                // http 요청 객체 종료
                httpURLConnection.disconnect();
            }
        }
        return redirect_pc_url;
    }

    // 간단한 json에서 파라미터 key에 대한 value를 찾기 위함
    private String getVersion(String json, String key){
        int keyIndex = json.indexOf(key);
        if(keyIndex == -1){
            return null;
        }

        // keyIndex로부터 : 찾기
        int startIndex = json.indexOf(":", keyIndex)+2; // value시작점
        // value이후 있을 쌍따움표 찾기
        int endIndex = json.indexOf("\"",startIndex);

        // substring은 endIndex-1 까지 보여주지만 위에서 찾은 endIndex는 쌍따옴표 위치이므로 그냥 실행
        return json.substring(startIndex,endIndex);

    }
}
