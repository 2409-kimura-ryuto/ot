package com.example.ot.controller.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = {PastAndFutureValidator.class})
@Target({ ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface PastAndFuture {
    String message() default "終了日は開始日よりも後になるようにしてください";

    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

    String start();
    String end();

    @Target(ElementType.FIELD)
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    public static @interface List {
        PastAndFuture[] value();
    }
}
