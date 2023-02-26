package com.study.MovieInfoBoard.config;

import com.study.MovieInfoBoard.intercepter.HttpInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new HttpInterceptor())
            .addPathPatterns("/board/movie/**")
            .excludePathPatterns("/board/movie/signin")
            .excludePathPatterns("/board/movie/signup")
            .excludePathPatterns("/board/movie/signinpro")
            .excludePathPatterns("/board/movie/logout");
    }
}
