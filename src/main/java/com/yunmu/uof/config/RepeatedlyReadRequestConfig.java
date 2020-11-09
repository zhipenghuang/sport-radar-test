package com.yunmu.uof.config;

import lombok.Data;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author hzp
 * @create 2018/9/19.
 */
@Configuration
@Data
public class RepeatedlyReadRequestConfig {

    @Bean
    public FilterRegistrationBean setFilter() {

        FilterRegistrationBean filterBean = new FilterRegistrationBean();
        filterBean.setFilter(new RepeatedlyReadRequestFilter());
        filterBean.addUrlPatterns("/*");
        return filterBean;
    }
}
