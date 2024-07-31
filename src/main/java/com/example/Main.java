package com.example;

import com.github.lalyos.jfiglet.FigletFont;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;

// 안에 많은 기능들이 있지만 이 클래스를 기준으로 하위 디렉토리에 대해서 컴포넌트 스캔을 시작한다
@SpringBootApplication
public class Main {
    public static void main(String[] args)  {

        // 의존관계 역전 -> SpringApplication으로 제어 흐름이 넘어감
        SpringApplication.run(Main.class, args);
    }

    public static void testFlow() throws IOException {
        String asciiArt = FigletFont.convertOneLine("Hello World!!!"); // 정적메소드사용시 클래스와 함께 사용시 Class명까지만 import
        System.out.println(asciiArt);
    }
}
