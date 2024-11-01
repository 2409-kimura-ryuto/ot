package com.example.ot.config;

import com.example.ot.filter.AdminFilter;
import com.example.ot.filter.LoginFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FilterConfig {
    //ログインフィルター
    @Bean
    public FilterRegistrationBean<LoginFilter> loginFilter() {
        FilterRegistrationBean<LoginFilter> bean = new FilterRegistrationBean<>();

        bean.setFilter(new LoginFilter());
        //ログイン情報が必要なURL
        bean.addUrlPatterns("/top");
        bean.addUrlPatterns("/new");
        bean.addUrlPatterns("/user-management");
        bean.addUrlPatterns("/user-registration");
        bean.addUrlPatterns("/user-edit-*");
        bean.setOrder(1);
        return bean;
    }

    //管理者権限フィルター
    @Bean
    public FilterRegistrationBean<AdminFilter> adminFilter() {
        FilterRegistrationBean<AdminFilter> bean = new FilterRegistrationBean<>();

        bean.setFilter(new AdminFilter());
        //管理者権限が必要なURL
        bean.addUrlPatterns("/user-management");
        bean.addUrlPatterns("/user-registration");
        bean.addUrlPatterns("/user-edit-*");
        bean.setOrder(2);
        return bean;
    }
}
