package com.example.ot.config;

import com.example.ot.filter.LoginFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {
    @Bean
    public FilterRegistrationBean<LoginFilter> loginFilter() {
        FilterRegistrationBean<LoginFilter> bean = new FilterRegistrationBean<>();

        bean.setFilter(new LoginFilter());
        //ログイン情報が必要なURL
        bean.addUrlPatterns("/");
        bean.addUrlPatterns("/add-message");
        bean.addUrlPatterns("/userList");
        bean.setOrder(1);
        return bean;
    }
}
