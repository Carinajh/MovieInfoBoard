package com.study.MovieInfoBoard.service.impl;

import com.study.MovieInfoBoard.data.dto.MovieinfoDto;
import com.study.MovieInfoBoard.data.entity.MovieinfoEntity;
import com.study.MovieInfoBoard.data.repository.MoveinfoRepository;
import com.study.MovieInfoBoard.service.MovieinfoService;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MovieinfoServiceImpl implements MovieinfoService {

    Logger LOGGER = LoggerFactory.getLogger(MovieinfoService.class);
    private MoveinfoRepository moveinfoRepository;

    @Autowired
    public MovieinfoServiceImpl(MoveinfoRepository moveinfoRepository) {
        this.moveinfoRepository = moveinfoRepository;
    }

    @Override
    public List<MovieinfoEntity> getMovieInfoList() {
        LOGGER.info("[getMovieInfoList] 호출");
        return moveinfoRepository.findAll();
    }

    @Override
    public MovieinfoEntity getMovieInfoView(Integer id) {
        LOGGER.info("[getMovieInfoView] 호출 : id = {}",id);
        return moveinfoRepository.findById(id).get();
    }
}
