package com.example.service;

import com.example.entity.Person;
import com.example.repository.PersonRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@RunWith(SpringRunner.class) // 구현체를 등록해야하기 때문에 Spring 실행흐름을 실행시켜야한다.
@SpringBootTest // 컴포넌트 스캔 및 SpringTest객체 사용등에 필요
public class PersonServiceTest {

    @Autowired
    private PersonRepository personRepository;

    // sequence SQL 생성을 위한 영속성 매니저
    @PersistenceContext // EntityManger 의존성 주입을 위한 어노테이션(Spring 컨테이너가 구현된 인스턴스 생성)
    private EntityManager entityManager;

    /***
     * h2 DB TEST를 위한 SEQUENCE 생성 메소드
     */
    @Before // Test가 실행되기전 초기화나 의존성주입이 필요한 경우에 사용하며 Test전 먼저 이 메소드가 먼저 실행되게 된다. PostConstruct와 다른점은 Before(Each)는 트랜잭션 처리를 지원한다는 것이다.
    @Transactional // entityManager을 이용할 때는 트랜잭션 범위를 설정해 줘야 하기 때문에 이 어노테이션이 사용된다.
    public void init(){
        // SQL 실행
        entityManager
                .createNativeQuery("CREATE SEQUENCE IF NOT EXISTS person_sequence START WITH 1 INCREMENT BY 1")
                .executeUpdate();
    }



    /***
     * jpa 기능 및 자동 PK 값 삽입등 테스트
     */
    @Test
    @Transactional // update/delete query가 있기 때문에 트랜잭션 범위를 지정해줘야 한다.
    public void findPersonTest(){
        // given
        Person person = new Person();
        person.setName("person");

        // when
        personRepository.save(person);

        // then
        // 위 시작점에서 PK를 따로 세팅하지 않았지만 저장 이후 PK가 생긴것을 볼 수 있음
        Assert.assertEquals(1L,person.getId().longValue());
        // 저장후 id를 통해 찾은값이 null이 아니여야함
        Assert.assertNotNull(personRepository.findById(person.getId()).orElse(null));
    }
}
