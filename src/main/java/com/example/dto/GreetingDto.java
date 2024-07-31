package com.example.dto;

import com.example.customInterface.ObjectToJson;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
@ObjectToJson
public class GreetingDto {

    private final String id;

    private final String name;
}
