package com.example.repository;

import com.example.entity.Person;
import org.springframework.data.repository.Repository;

import java.util.Optional;

public interface PersonRepository extends Repository<Person,Long> {

    // 형식에 맞게 메소드명만 정의하면 구현체가(Spring Data Jpa) 자동 구현
    Person save(Person person);

    Optional<Person> findById(Long id);
}
