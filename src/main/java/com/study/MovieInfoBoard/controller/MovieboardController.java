package com.study.MovieInfoBoard.controller;

import com.study.MovieInfoBoard.data.entity.MovieinfoEntity;
import com.study.MovieInfoBoard.service.MovieinfoService;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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
            rtn = "movie-view";
        } else if (action.equals("edit")) {
            LOGGER.info("[movieinfoaction] edit버튼 동작호출");
            entity = movieinfoService.getMovieInfoView(id);
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
    public String movieinfoadd(MovieinfoEntity movieinfo,Model model, MultipartFile multipartfile,String action){
        LOGGER.info("[movieinfoadd-pro] 호출");
        String rtn = "";
        if(action.equals("cancel")){
            LOGGER.info("[movieinfoadd-pro] 등록취소버튼 동작호출");
            rtn ="movie-all-list";
        }else if (action.equals("add")){
            LOGGER.info("[movieinfoadd-pro] 등록버튼 동작호출");

            movieinfoService.write(movieinfo,multipartfile);

            rtn ="movie-all-list";
        }else{
            LOGGER.info("[movieinfoadd-pro] 알수없는 동작호출");
            rtn ="movie-all-list";
        }
        return rtn;
    }
}
