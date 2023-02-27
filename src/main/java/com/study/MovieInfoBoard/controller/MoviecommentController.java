package com.study.MovieInfoBoard.controller;

import com.study.MovieInfoBoard.config.CommonConfig;
import com.study.MovieInfoBoard.data.entity.MoviecommentEntity;
import com.study.MovieInfoBoard.data.entity.MovieinfoEntity;
import com.study.MovieInfoBoard.data.entity.MovieuserEntity;
import com.study.MovieInfoBoard.data.repository.MoviecommentRepository;
import com.study.MovieInfoBoard.service.MoviecommentService;
import com.study.MovieInfoBoard.service.MovieinfoService;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
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
public class MoviecommentController {
    Logger LOGGER = LoggerFactory.getLogger(MoviecommentController.class);
    private MoviecommentService moviecommentService;

    @Autowired
    public MoviecommentController(MoviecommentService moviecommentService) {
        this.moviecommentService = moviecommentService;
    }

//    @GetMapping("/view")
//    public void Moviecommentfind(Model model) {
//        LOGGER.info("[Moviecommentfind] 호출");
//        LOGGER.info("[Moviecommentfind] {}",
//                ((MovieinfoEntity) model.getAttribute("movieinfoentity")).toString());
//
//        int movieinfoid = ((MovieinfoEntity) model.getAttribute("movieinfoentity")).getId();
//        LOGGER.info("[Moviecommentfind] {}",movieinfoid);
//        List<MoviecommentEntity> moviecommentEntities = moviecommentService.findByInfoid(
//            movieinfoid);
//        long cnt = moviecommentService.countByInfoid(movieinfoid);
//        model.addAttribute("moviecommententity", moviecommentEntities);
//        model.addAttribute("moviecommentcnt",cnt);
//
//    }

    @PostMapping("/addcomment")
    public String moviecommentAdd(MoviecommentEntity moviecomment,String comment,Model model,
        HttpServletRequest httpServletRequest){
        LOGGER.info("[moviecommentAdd] 호출 ");
        int movieinfoid = ((MovieinfoEntity) model.getAttribute("movieinfoentity")).getId();
        HttpSession session = httpServletRequest.getSession();
        MovieuserEntity movieuserEntity = (MovieuserEntity)session.getAttribute(CommonConfig.LOGIN_USER);
        MoviecommentEntity moviecommentEntity = new MoviecommentEntity();
        moviecommentEntity.setUserid(movieuserEntity.getUserid());
        moviecommentEntity.setComment(comment);
        moviecommentEntity.setInfoid(movieinfoid);
        moviecommentService.saveComment(moviecommentEntity);

//        Moviecommentfind(model);

        return "/board/movie/view";
    }
}
