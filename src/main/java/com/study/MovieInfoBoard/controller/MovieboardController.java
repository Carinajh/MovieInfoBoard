package com.study.MovieInfoBoard.controller;

import com.study.MovieInfoBoard.config.CommonConfig;
import com.study.MovieInfoBoard.data.entity.MoviecommentEntity;
import com.study.MovieInfoBoard.data.entity.MovieinfoEntity;
import com.study.MovieInfoBoard.data.entity.MovieuserEntity;
import com.study.MovieInfoBoard.service.MoviecommentService;
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

    private MoviecommentService moviecommentService;

//    @Autowired
//    public MovieboardController(MovieinfoService movieinfoService) {
//        this.movieinfoService = movieinfoService;
//    }
//    @Autowired
//    public MovieboardController(MoviecommentService moviecommentService) {
//        this.moviecommentService = moviecommentService;
//    }

    @Autowired
    public MovieboardController(MovieinfoService movieinfoService,
        MoviecommentService moviecommentService) {
        this.movieinfoService = movieinfoService;
        this.moviecommentService = moviecommentService;
    }


    //전체 영화 리스트 표시
    @GetMapping("/list")
//    public String movieinfolist(Model model,HttpServletRequest httpServletRequest){
    public String movieinfolist(Model model){
        LOGGER.info("[movieinfolist] 호출");
        List<MovieinfoEntity> list = movieinfoService.listOpeningdateDesc();

        model.addAttribute("list",list);
//        HttpSession session = httpServletRequest.getSession();
//        MovieuserEntity movieuserEntity = (MovieuserEntity)session.getAttribute(CommonConfig.LOGIN_USER);
//        if (movieuserEntity == null){
//            return"sign-in";
//        }
//        LOGGER.info("[session] {}",movieuserEntity.getId());
//        LOGGER.info("[session] {}",movieuserEntity.getName());
//        LOGGER.info("[session] {}",movieuserEntity.getUserpw());
//        LOGGER.info("[session] {}",movieuserEntity.getUserid());
//        LOGGER.info("[session] {}",movieuserEntity.getEmail());
        return "movie-all-list";
    }

    //전체 영화 리스트 화면 보기,수정버튼처리
    @PostMapping("/action/{id}")
    public String movieinfoaction(@PathVariable("id") Integer id,Model model,String action,
        HttpServletRequest httpServletRequest){
        LOGGER.info("[movieinfoaction] 호출 id : {} action : {}",id,action);

        String rtn = "";
        MovieinfoEntity entity;
        if(action.equals("view")){
            LOGGER.info("[movieinfoaction] view버튼 동작호출");
            entity = movieinfoService.getMovieInfoView(id);
            model.addAttribute("movieinfoentity",entity);
            movieinfoService.updateView(id);
            Moviecommentfind(model);

            rtn = "movie-view";
        } else if (action.equals("edit")) {
            LOGGER.info("[movieinfoaction] edit버튼 동작호출");
            entity = movieinfoService.getMovieInfoView(id);
            LOGGER.info("[movieinfoaction] {}",entity.getOpeningdate());
            model.addAttribute("movieinfoentity",entity);
            rtn = "movie-modify";
        }else{
            LOGGER.info("[movieinfoaction] 알수없는 동작호출");
            rtn = "movie-all-list";
        }

        return rtn;
    }

//    영화 정보추가
    @GetMapping("/add")
    public String movieinfoadd(){
        LOGGER.info("[movieinfoadd] 호출");

        return "movie-add";
    }

//    영화정보추가 화면 버튼처리
    @PostMapping("/addpro")
    public String movieinfoadd(MovieinfoEntity movieinfo,Model model,String action,MultipartFile multipartFile,
        @RequestParam("openingdate") @DateTimeFormat(pattern = "yyyy-MM-dd") Date openingdate,
        HttpServletRequest httpServletRequest){
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
            HttpSession session = httpServletRequest.getSession();
            movieinfo.setDelflg('f');
            movieinfo.setUserid(((MovieuserEntity)session.getAttribute(CommonConfig.LOGIN_USER)).getUserid());
            movieinfoService.write(movieinfo,multipartFile);

        }else{
            LOGGER.info("[movieinfoadd-pro] 알수없는 동작호출");
        }
        return "redirect:/board/movie/list";
    }

//    영화정보 수정화면 버튼처리
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

//    영화정보 삭제 처리
    @GetMapping("/delete/{id}")
    public String movieinfodelete(@PathVariable("id") Integer id){
        LOGGER.info("[movieinfodelete] 호출 id : {} ",id);

        movieinfoService.deleteByMovieinfo(id);

        return "redirect:/movie-all-list";
    }


//    @PostMapping("/addcomment")
//    public String moviecommentAdd(MoviecommentEntity moviecomment,String comment,Model model,HttpServletRequest httpServletRequest){
//        LOGGER.info("[moviecommentAdd] 호출 ");
//        int movieinfoid = ((MovieinfoEntity) model.getAttribute("movieinfoentity")).getId();
//        HttpSession session = httpServletRequest.getSession();
//        MovieuserEntity movieuserEntity = (MovieuserEntity)session.getAttribute(CommonConfig.LOGIN_USER);
//        MoviecommentEntity moviecommentEntity = new MoviecommentEntity();
//        moviecommentEntity.setUserid(movieuserEntity.getUserid());
//        moviecommentEntity.setComment(comment);
//        moviecommentEntity.setInfoid(movieinfoid);
//        moviecommentService.saveComment(moviecommentEntity);
//
//        Moviecommentfind(model);
//
//        return "/board/movie/view";
//    }


    public void Moviecommentfind(Model model) {
        LOGGER.info("[Moviecommentfind] 호출");
        LOGGER.info("[Moviecommentfind] {}",
            ((MovieinfoEntity) model.getAttribute("movieinfoentity")).toString());

        int movieinfoid = ((MovieinfoEntity) model.getAttribute("movieinfoentity")).getId();
        LOGGER.info("[Moviecommentfind] {}",movieinfoid);
        List<MoviecommentEntity> moviecommentEntities = moviecommentService.findByInfoid(
            movieinfoid);

        long cnt = moviecommentService.countByInfoid(movieinfoid);
        LOGGER.info("[Moviecommentfind] {}",cnt);
        model.addAttribute("moviecommententity", moviecommentEntities);
        model.addAttribute("moviecommentcnt",cnt);

    }


}
