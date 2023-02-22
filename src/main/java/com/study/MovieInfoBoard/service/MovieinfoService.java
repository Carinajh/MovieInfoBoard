package com.study.MovieInfoBoard.service;

import com.study.MovieInfoBoard.data.dto.MovieinfoDto;
import com.study.MovieInfoBoard.data.entity.MovieinfoEntity;
import java.util.List;
import org.springframework.web.multipart.MultipartFile;

public interface MovieinfoService {

    List<MovieinfoEntity> getMovieInfoList();

    MovieinfoEntity getMovieInfoView(Integer id);

    void write(MovieinfoEntity entity, MultipartFile multipartFile);
}
