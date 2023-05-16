package org.koreait.configs.interceptors;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

/*
* 사이트 설정 유지
* 사이트 전역에 공통부분으로 쓰이는 것들 설정
* */
@Component
public class SiteConfigInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        request.setAttribute("cssJsVersion", 1);

        return true;
    }



}
