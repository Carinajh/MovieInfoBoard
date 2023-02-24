package com.study.MovieInfoBoard.controller;

import com.study.MovieInfoBoard.config.CommonConfig;
import com.study.MovieInfoBoard.data.entity.MovieinfoEntity;
import com.study.MovieInfoBoard.data.entity.MovieuserEntity;
import com.study.MovieInfoBoard.service.MovieinfoService;
import com.study.MovieInfoBoard.service.MovieuserService;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/board/movie")
public class MovieboardController {

    Logger LOGGER = LoggerFactory.getLogger(MovieboardController.class);
    private MovieinfoService movieinfoService;
    private MovieuserService movieuserService;

    @Autowired
    public MovieboardController(MovieinfoService movieinfoService,
        MovieuserService movieuserService) {
        this.movieinfoService = movieinfoService;
        this.movieuserService = movieuserService;
    }

    @GetMapping("/list")
    public String movieinfolist(Model model){
        LOGGER.info("[movieinfolist] 호출");
        List<MovieinfoEntity> list = movieinfoService.listOpeningdateDesc();

        model.addAttribute("list",list);
        model.addAttribute("elapsed time","");
        return "movie-all-list";
    }

    @PostMapping("/action/{id}")
    public String movieinfoaction(@PathVariable("id") Integer id,Model model,String action){
        LOGGER.info("[movieinfoaction] 호출 id : {} action : {}",id,action);

        String rtn = "";
        MovieinfoEntity entity;
        if(action.equals("view")){
            LOGGER.info("[movieinfoaction] view버튼 동작호출");
            entity = movieinfoService.getMovieInfoView(id);
            model.addAttribute("movieinfoentity",entity);
            movieinfoService.updateView(id);
            rtn = "movie-view";
        } else if (action.equals("edit")) {
            LOGGER.info("[movieinfoaction] edit버튼 동작호출");
            entity = movieinfoService.getMovieInfoView(id);
            LOGGER.info("[movieinfoaction] {}}",entity.getOpeningdate());
            model.addAttribute("movieinfoentity",entity);
            rtn = "movie-modify";
        }else{
            LOGGER.info("[movieinfoaction] 알수없는 동작호출");
            rtn = "movie-all-list";
        }

        return rtn;
    }

    @GetMapping("/add")
    public String movieinfoadd(){
        LOGGER.info("[movieinfoadd] 호출");

        return "movie-add";
    }
    @PostMapping("/addpro")
    public String movieinfoadd(MovieinfoEntity movieinfo,Model model,String action,MultipartFile multipartFile,
        @RequestParam("openingdate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date openingdate){
        LOGGER.info("[movieinfoadd-pro] 호출");
        LOGGER.info("[movieinfoadd-pro] {}",multipartFile.getOriginalFilename());
        LOGGER.info("getFilepath :{}",movieinfo.getFilepath());
        LOGGER.info("getFilename :{}",movieinfo.getFilename());
        LOGGER.info("getHit :{}",movieinfo.getHit());
        LOGGER.info("getCreated:{}",movieinfo.getCreated());
        LOGGER.info("getDelflg:{}",movieinfo.getDelflg());
        LOGGER.info("getActor:{}",movieinfo.getActor());
        LOGGER.info("getUpdated:{}",movieinfo.getUpdated());
        LOGGER.info("getUserid:{}",movieinfo.getUserid());
        LOGGER.info("getId:{}",movieinfo.getId());
        LOGGER.info("getContent:{}",movieinfo.getContent());
        LOGGER.info("getTitle:{}",movieinfo.getTitle());
        LOGGER.info("getOpeningdate:{}",movieinfo.getOpeningdate());
        if(action.equals("cancel")){
            LOGGER.info("[movieinfoadd-pro] 등록취소버튼 동작호출");

        }else if (action.equals("add")){
            LOGGER.info("[movieinfoadd-pro] 등록버튼 동작호출");
            movieinfo.setDelflg('f');
            movieinfoService.write(movieinfo,multipartFile);

        }else{
            LOGGER.info("[movieinfoadd-pro] 알수없는 동작호출");
        }
        return "redirect:/board/movie/list";
    }

    @PostMapping("/modifypro/{id}")
    public String movieinfomodify(@PathVariable("id") Integer id,MovieinfoEntity movieinfo,Model model,String action,MultipartFile multipartFile,
        @RequestParam("openingdate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date openingdate){
        LOGGER.info("[movieinfoadd-pro] 호출");
        MovieinfoEntity temp = movieinfoService.getMovieInfoView(id);
        if(action.equals("cancel")){
            LOGGER.info("[movieinfoadd-pro] 등록취소버튼 동작호출");

        }else if (action.equals("modify")) {
            LOGGER.info("[movieinfoadd-pro] 등록버튼 동작호출");
//           기존정보업데이트
            temp.setId(temp.getId());
            temp.setTitle(movieinfo.getTitle());
            temp.setContent(movieinfo.getContent());
            temp.setHit(temp.getHit());
            temp.setActor(movieinfo.getActor());
            temp.setDelflg(movieinfo.getDelflg());
            temp.setOpeningdate(movieinfo.getOpeningdate());

            movieinfoService.write(movieinfo, multipartFile);
        }else if(action.equals("delete")){
            LOGGER.info("[movieinfoadd-pro] 삭제버튼 동작호출");
        }else{
            LOGGER.info("[movieinfoadd-pro] 알수없는 동작호출");
        }
        return "redirect:/board/movie/list";
    }

    @GetMapping("/delete/{id}")
    public String movieinfodelete(@PathVariable("id") Integer id){
        LOGGER.info("[movieinfodelete] 호출 id : {} ",id);

        movieinfoService.deleteByMovieinfo(id);

        return "redirect:/movie-all-list";
    }

//로그인 화면 표시
    @GetMapping("signin")
    public  String signin(){
        LOGGER.info("[signin] 호출 ");

        return "sign-in";
    }

//    로그인화면 로그인 & 회원가입 버튼동작처리
    @PostMapping("signup")
    public  String signup(MovieuserEntity movieuserEntity,String action,Model model,
        HttpServletRequest httpServletRequest){
        LOGGER.info("[signup] 호출 ");
        String rtn="";
        boolean temp;
        if(action.equals("signin")){
            LOGGER.info("[signup] sign in 버튼호출 ");
            //로그인기능 구현
            if( movieuserService.Loginmovieuser(movieuserEntity)){
                //ID/PW확인완료.세션발급.
                HttpSession session = httpServletRequest.getSession();// 세션 반환, 없으면 신규 세션 생성하여 반환
                session.setAttribute(CommonConfig.LOGIN_USER,movieuserEntity);// 세션에 회원 정보 보관
                rtn="redirect:/board/movie/list";
            }else{
                //문제 발생.
                model.addAttribute("signinFail",true);
                rtn="sign-in";
            }


        } else if (action.equals("signup")) {
            LOGGER.info("[signup] sign up 버튼호출 ");
            rtn="movie-signup";
        } else{
            LOGGER.info("[signup] 알수없는 호출 ");
            rtn="sign-in";
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
            rtn = "sign-in";
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
            rtn = "sign-in";
        }
        return rtn;
    }


}
