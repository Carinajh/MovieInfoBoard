package com.study.MovieInfoBoard.data.repository;

import com.study.MovieInfoBoard.data.entity.MovieuserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieuserRepository extends JpaRepository<MovieuserEntity,Integer> {

    MovieuserEntity findByUserid(String userid);


}
