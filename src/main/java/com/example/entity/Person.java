package com.example.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter // Entity의 Setter는 지양점이지만 테스트용으로 일단 사용한다.
@Entity // 이 클래스는 Entity클래스인걸 명시한다. javax(java extension으로 추가적인기능)에 포함되어있다.
@SequenceGenerator(name = "person_seq", sequenceName = "person_sequence", allocationSize = 1) // 자동생성전략 SEQUENCE를 사용하기 위한 Generator
public class Person {
    @Id // Table KEY값에 해당하는 필드
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "person_seq") // 영속성 제공자가 PK를 할당한다. -> auto_increment, mysql은 identity Type, oracle이나 h2 DB는 시퀀스 타입이다.
    private Long id;
    @Column // 컬럼 명시
    private String name;
}
