package com.example.ot.controller.validator;

import io.micrometer.common.util.StringUtils;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import java.sql.Date;

public class PastAndFutureValidator implements ConstraintValidator<PastAndFuture, Object> {
    private String start;//チェックに使う項目
    private String end;//チェックに使う項目

    @Override
    public void initialize(PastAndFuture annotation) {
        this.start = annotation.start();
        this.end = annotation.end();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        BeanWrapper beanWrapper = new BeanWrapperImpl(value);
        String startDate = (String) beanWrapper.getPropertyValue(start);
        String endDate   = (String) beanWrapper.getPropertyValue(end);
        return StringUtils.isBlank(startDate) || StringUtils.isBlank(endDate) || Date.valueOf(startDate).before(Date.valueOf(endDate));
    }
}
