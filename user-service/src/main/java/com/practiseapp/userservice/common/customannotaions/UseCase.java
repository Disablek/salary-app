package com.practiseapp.userservice.common.customannotaions;

import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Component;

import java.lang.annotation.*;

@Component
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface UseCase {

    @AliasFor(annotation = Component.class)
    String value() default "";
}
