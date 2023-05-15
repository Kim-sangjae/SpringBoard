package org.koreait.configs;


import lombok.RequiredArgsConstructor;
import org.koreait.configs.interceptors.SiteConfigInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class MvcConfig implements WebMvcConfigurer {

    //파일업로드 부분 설정 properties에 설정한 경로를 지정해준다
    @Value("${file.upload.path}")
    private String fileUploadPath;


    // 사이트 설정 유지 인터셉터 (인터셉터 클래스에 @component 붙였는지 확인)
    private final SiteConfigInterceptor siteConfigInterceptor;


    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/")
                .setViewName("main/index");
    }


    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations("file:///"+fileUploadPath);
    }


    //인터셉터
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
       registry.addInterceptor(siteConfigInterceptor)
               .addPathPatterns("/**");
    }


}
