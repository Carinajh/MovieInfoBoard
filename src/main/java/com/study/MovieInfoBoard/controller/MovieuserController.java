package com.study.MovieInfoBoard.controller;

import com.study.MovieInfoBoard.config.CommonConfig;
import com.study.MovieInfoBoard.data.entity.MovieuserEntity;
import com.study.MovieInfoBoard.service.MovieinfoService;
import com.study.MovieInfoBoard.service.MovieuserService;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/board/movie")
public class MovieuserController {

    Logger LOGGER = LoggerFactory.getLogger(MovieboardController.class);
    private MovieuserService movieuserService;
    //로그인 화면 표시
    @GetMapping("signin")
    public  String signin(){
        LOGGER.info("[signin] 호출 ");

        return "sign-in";
    }

    @Autowired
    public MovieuserController(MovieuserService movieuserService) {
        this.movieuserService = movieuserService;
    }

    //    로그인화면 로그인 & 회원가입 버튼동작처리
    @PostMapping("signup")
    public  String signup(MovieuserEntity movieuserEntity,String action, Model model,
        HttpServletRequest httpServletRequest){
        LOGGER.info("[signup] 호출 ");
        String rtn="";

        if(action.equals("signin")){
            LOGGER.info("[signup] sign in 버튼호출 ");
            //로그인기능 구현
            MovieuserEntity temp = movieuserService.Loginmovieuser(movieuserEntity);
            if( temp != null && temp.getUserpw().equals(movieuserEntity.getUserpw())){
                //ID/PW확인완료.세션발급.
                LOGGER.info("[signup] ID/PW확인완료 ");
                HttpSession session = httpServletRequest.getSession();// 세션 반환, 없으면 신규 세션 생성하여 반환
                session.setAttribute(CommonConfig.LOGIN_USER,temp);// 세션에 회원 정보 보관
                session.setMaxInactiveInterval(300000); //세션 5분설정
                LOGGER.info("[signup] ID/PW확인완료 ");
                rtn="redirect:/board/movie/list";
            }else{
                //입력정보 불일치.
                model.addAttribute("userinfoFail",true);
                rtn="redirect:/board/movie/signin";
            }


        } else if (action.equals("signup")) {
            LOGGER.info("[signup] sign up 버튼호출 ");
            rtn="movie-signup";
        } else{
            LOGGER.info("[signup] 알수없는 호출 ");
            rtn="redirect:/board/movie/signin";
        }
        return rtn;
    }
    //    회원가입화면처리
    @PostMapping("signinpro")
    public  String signinpro(MovieuserEntity movieuserEntity,String action,
        Model model){
        LOGGER.info("[signinpro] 호출 ");
        LOGGER.info("[signinpro] {} ",action);
        String rtn = "";

        if(action.equals("cancel")){
            LOGGER.info("[signinpro] 등록취소버튼 호출 ");
            rtn = "redirect:/board/movie/signin";
        } else if (action.equals("add")) {
            LOGGER.info("[signinpro] 유저등록버튼 호출 ");
            boolean temp = movieuserService.createMovieuser(movieuserEntity);
            if(temp == true){
                //입력정보로 회원가입 진행완료
                LOGGER.info("[signinpro] 회원가입 진행완료 ");
                rtn="movie-all-list";
            }else{
                //입력ID존재함
                LOGGER.info("[signinpro] 입력ID존재함 ");
                model.addAttribute("useridFail", true);
                rtn = "movie-signup";
            }

        }else{
            LOGGER.info("[signinpro] 알수없는 호출 ");
            rtn = "redirect:/board/movie/signin";
        }
        return rtn;
    }

    @GetMapping("logout")
    public String logout(HttpServletRequest req, HttpServletResponse res,Model model){
        HttpSession session = req.getSession();
        LOGGER.info("[logout] 호출 ");
        if(session.getAttribute(CommonConfig.LOGIN_USER) != null){
            session.removeAttribute(CommonConfig.LOGIN_USER);
            session.invalidate();
        }

        return "redirect:/board/movie/signin";
    }
}
