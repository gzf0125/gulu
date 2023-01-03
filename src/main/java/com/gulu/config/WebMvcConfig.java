package com.gulu.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
//Mvc视图解释器,配置类
@Slf4j
@Configuration
public class WebMvcConfig extends WebMvcConfigurationSupport {
    /*
    设置静态资源映射
    @param registry
     */
    @Override//重写
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {//classpath对于resource目录
        log.info("开始进行静态资源映射");
        registry.addResourceHandler("/backend/**").addResourceLocations("classpath:/backend/");//资源处理器
        registry.addResourceHandler("/front/**").addResourceLocations("classpath:/front/");//资源处理器

    }
}
