package com.iyyxx.springboot4ruiji.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 * @className: WebMvcConfig
 * @description: TODO 类描述
 * @author: eric 4575252@gmail.com
 * @date: 2022/9/28/0028 14:35:53
 **/
@Slf4j
@Configuration
public class WebMvcConfig extends WebMvcConfigurationSupport {
    /*** 设置静态资源映射
     * @param registry
     */
    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        log.info("开始进行静态资源映射...");
        registry.addResourceHandler("/backend/**").addResourceLocations("classpath:/backend/");
        registry.addResourceHandler("/front/**").addResourceLocations("classpath:/front/");
    }
}
