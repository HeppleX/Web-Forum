package com.forum.buge_server.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;

/**
 * @author
 * @date 2020-05-16
 **/

@Configuration
public class MyMvcConfig implements WebMvcConfigurer {

    /**
     * 配置跨域
     * 
     * @param registry
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        // 允许所有域名的访问
        registry.addMapping("/**").allowedHeaders("*").allowedOrigins("*").allowCredentials(true)
                .allowedMethods("GET", "POST", "DELETE", "PUT", "OPTIONS").maxAge(3600).allowCredentials(true);
        // registry.allowCredentials(true);
        // 设置是否允许客户端发送cookie信息。默认是false
    }

    /**
     * 拦截器配置 这里如果按正常方法配置 swagger 测试页面将无法进入 这个是加入了一个映射路径并且先把所有的排除路径先加入在进行拦截筛选
     * 
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        /**
         * 定义一个登陆拦截器,拦截所有请求
         */
        List<String> excludePathPatterns = new ArrayList<>();
        // 排除 swagger 测试页面
        excludePathPatterns.add("/swagger-ui.html");
        excludePathPatterns.add("/swagger-resources/**");
        excludePathPatterns.add("/error");
        excludePathPatterns.add("/webjars/**");
        // 排除未登录即可访问的接口
        excludePathPatterns.add("/open/**");
        excludePathPatterns.add("/sys/login");

        // addPathPatterns 用于添加拦截规则 ， 先把所有路径都加入拦截， 再一个个排除
        registry.addInterceptor(new TokenInterceptor()).addPathPatterns("/**").excludePathPatterns(excludePathPatterns);
    }

    /**
     * 映射文件路径配置，文件上传存放路径 文件上传后可以通过 如: /file/文件名 即为该文件的url地址
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

}
