package com.study.MovieInfoBoard.service.impl;

import com.study.MovieInfoBoard.data.entity.MovieuserEntity;
import com.study.MovieInfoBoard.data.repository.MovienfoRepository;
import com.study.MovieInfoBoard.data.repository.MovieuserRepository;
import com.study.MovieInfoBoard.service.MovieinfoService;
import com.study.MovieInfoBoard.service.MovieuserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MoveuserServiceImpl implements MovieuserService {

    Logger LOGGER = LoggerFactory.getLogger(MoveuserServiceImpl.class);
    private MovieuserRepository movieuserRepository;

    @Autowired
    public MoveuserServiceImpl(MovieuserRepository movieuserRepository) {
        this.movieuserRepository = movieuserRepository;
    }

    @Override
    public MovieuserEntity findByUserid(String userid) {
        LOGGER.info("[getMovieuserInfo] 호출 : userid = {}",userid);
        return movieuserRepository.findByUserid(userid);
    }

    public boolean createMovieuser(MovieuserEntity movieuserEntity){
        MovieuserEntity temp = movieuserRepository.findByUserid(movieuserEntity.getUserid());
//      LOGGER.info("[createMovieuser] 호출 : movieuserEntity = {}",movieuserEntity.toString());
        boolean flg ;
        if(temp != null){
            flg = false;
        }else{
            movieuserRepository.save(movieuserEntity);
            flg = true;
        }

        return flg;
    }

    public boolean Loginmovieuser(MovieuserEntity movieuserEntity){
        MovieuserEntity temp = movieuserRepository.findByUserid(movieuserEntity.getUserid());
        boolean flg;
        if(temp != null){
            if (temp.getUserpw().equals(movieuserEntity.getUserpw())){
                flg= true;
            }else {
                flg=false;
            }
        }else {
            flg = false;
        }
        return flg;
    }
}
