package com.ll.exam.app10.app.base;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Value("${custom.genFileDirPath}")
    private String genFileDirPath;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // addResourceHandler는 주소가 "/gen/**" 이렇게 날라왔을 때
        // 그것을 "file:///" + genFileDirPath + "/" 여기에서 찾는다.
        registry.addResourceHandler("/gen/**")
                .addResourceLocations("file:///" + genFileDirPath + "/");
    }
}
