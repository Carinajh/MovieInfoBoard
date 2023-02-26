package com.study.MovieInfoBoard.intercepter;

import com.study.MovieInfoBoard.config.CommonConfig;
import com.study.MovieInfoBoard.data.entity.MovieuserEntity;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class HttpInterceptor implements HandlerInterceptor {
    //인터셉터를 활용하여 페이지 이동시에 세션확인실시
    private final Logger LOGGER = LoggerFactory.getLogger(HttpInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
        Object handler) throws Exception {

        MovieuserEntity temp = (MovieuserEntity)request.getSession().getAttribute(CommonConfig.LOGIN_USER);
        if(temp != null){
            LOGGER.info("[preHandle] preHandle userid : {}",temp.getUserid());
            return true;
        }else{
            LOGGER.info("[preHandle] preHandle is performed");
            LOGGER.info("[preHandle] preHandle : {}",request);
            LOGGER.info("[preHandle] preHandle path info : {}",request.getPathInfo());
            LOGGER.info("[preHandle] preHandle header names: {}",request.getHeaderNames());
            LOGGER.info("[preHandle] preHandle request URL: {}",request.getRequestURL());
            LOGGER.info("[preHandle] preHandle request URI: {}",request.getRequestURI());
            LOGGER.info("[preHandle] preHandle requested Session Id : {}",request.getRequestedSessionId());
            response.sendRedirect("/board/movie/signin");
            return false;
        }
    }
}
