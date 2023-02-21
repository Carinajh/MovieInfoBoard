package com.study.MovieInfoBoard.service;

import com.study.MovieInfoBoard.data.dto.MovieinfoDto;
import com.study.MovieInfoBoard.data.entity.MovieinfoEntity;
import java.util.List;

public interface MovieinfoService {

    List<MovieinfoEntity> getMovieInfoList();

    MovieinfoEntity getMovieInfoView(Integer id);
}
