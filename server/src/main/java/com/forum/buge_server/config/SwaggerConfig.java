package com.forum.buge_server.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * swagger界面ui地址 http://localhost:8098/swagger-ui.html#/
 */

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    /**
     * 通过 createRestApi函数来构建一个DocketBean 函数名,可以随意命名,喜欢什么命名就什么命名
     */
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo())// 调用apiInfo方法,创建一个ApiInfo实例,里面是展示在文档页面信息内容
                .select().paths(PathSelectors.any()).build();
    }

    // 构建 api文档的详细信息函数
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                // 页面标题
                .title("部格服务端接口")
                // 条款地址
                .version("1.0")
                // 描述
                .description("API 描述：所有字段都未添加判断操作，如遇到500，请确认是否添加参数请求").build();
    }

}