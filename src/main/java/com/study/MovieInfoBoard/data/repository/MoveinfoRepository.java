package com.study.MovieInfoBoard.data.repository;

import com.study.MovieInfoBoard.data.entity.MovieinfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MoveinfoRepository extends JpaRepository<MovieinfoEntity,Integer> {

}
