//package com.study.MovieInfoBoard.controller;
//
//import com.study.MovieInfoBoard.config.CommonConfig;
//import com.study.MovieInfoBoard.data.entity.MovieuserEntity;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.SessionAttribute;
//
//@Controller
//public class SessionController {
//
//    Logger LOGGER = LoggerFactory.getLogger(SessionController.class);
//    @GetMapping("/")
//    public String home(@SessionAttribute(name = CommonConfig.LOGIN_USER,required = false)
//        MovieuserEntity movieuserEntity, Model model){
//        LOGGER.info("[home] 호출");
//        if(movieuserEntity == null){
//            return "sign-in";
//        }
//        model.addAttribute("list",movieuserEntity);
//
//        return "movie-all-list";
//    }
//}
