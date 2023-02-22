package com.study.MovieInfoBoard.controller;

import com.study.MovieInfoBoard.data.entity.MovieinfoEntity;
import com.study.MovieInfoBoard.service.MovieinfoService;
import java.util.Date;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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

    @Autowired
    public MovieboardController(MovieinfoService movieinfoService) {
        this.movieinfoService = movieinfoService;
    }

    @GetMapping("/list")
    public String movieinfolist(Model model){
        LOGGER.info("[movieinfolist] 호출");
        List<MovieinfoEntity> list = movieinfoService.getMovieInfoList();
        for (MovieinfoEntity entity: list
        ) {
            LOGGER.info("id : {}  title : {}  content : {}",entity.getId(),entity.getTitle(),entity.getContent());
        }
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
//        LOGGER.info("[movieinfoadd-pro] {}",multipartFile.getOriginalFilename());
//        LOGGER.info("getFilepath :{}",movieinfo.getFilepath());
//        LOGGER.info("getFilename :{}",movieinfo.getFilename());
//        LOGGER.info("getHit :{}",movieinfo.getHit());
//        LOGGER.info("getCreated:{}",movieinfo.getCreated());
//        LOGGER.info("getDelflg:{}",movieinfo.getDelflg());
//        LOGGER.info("getActor:{}",movieinfo.getActor());
//        LOGGER.info("getUpdated:{}",movieinfo.getUpdated());
//        LOGGER.info("getUserid:{}",movieinfo.getUserid());
//        LOGGER.info("getId:{}",movieinfo.getId());
//        LOGGER.info("getContent:{}",movieinfo.getContent());
//        LOGGER.info("getTitle:{}",movieinfo.getTitle());
//        LOGGER.info("getOpeningdate:{}",movieinfo.getOpeningdate());
        MovieinfoEntity temp = movieinfoService.getMovieInfoView(id);
        if(action.equals("cancel")){
            LOGGER.info("[movieinfoadd-pro] 등록취소버튼 동작호출");

        }else if (action.equals("modify")){
            LOGGER.info("[movieinfoadd-pro] 등록버튼 동작호출");
//           기존정보업데이트
            temp.setId(temp.getId());
            temp.setTitle(movieinfo.getTitle());
            temp.setContent(movieinfo.getContent());
            temp.setHit(temp.getHit());
            temp.setActor(movieinfo.getActor());
            temp.setDelflg(movieinfo.getDelflg());
            temp.setOpeningdate(movieinfo.getOpeningdate());
            movieinfoService.write(movieinfo,multipartFile);

        }else{
            LOGGER.info("[movieinfoadd-pro] 알수없는 동작호출");
        }
        return "redirect:/board/movie/list";
    }
}
