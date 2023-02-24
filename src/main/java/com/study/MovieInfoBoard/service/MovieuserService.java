package com.study.MovieInfoBoard.service;

import com.study.MovieInfoBoard.data.entity.MovieuserEntity;

public interface MovieuserService {

    MovieuserEntity findByUserid(String id);

}
