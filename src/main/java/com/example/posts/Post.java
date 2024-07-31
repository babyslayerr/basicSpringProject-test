package com.example.posts;

import lombok.*;

import javax.persistence.*;

@Getter
@Builder
@AllArgsConstructor // 매개변수 있는 생성자(빌드패턴 사용시 필요)
@NoArgsConstructor // 매개변수 없는 생성자(빌드패턴 사용시 필요)
@Entity
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO) // 참고로 identify는 DB에 key생성을 위임하는것인에 h2는 앙댐 , 후에 auto모드는 h2 db mode를 mysql로 바꾸면 identify로 바뀜 지금은 테스트용
    private Long id;

    @Column
    private String subject;

    @Column
    private String content;
}
