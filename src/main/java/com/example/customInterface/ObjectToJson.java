package com.example.customInterface;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME) // Retention.compile -> ex) @NotNull(source 사용시 .class로 작성된 라이브러리에 대해서 hint 제공불가) , Retention.source -> ex) @Getter
public @interface ObjectToJson {

}
