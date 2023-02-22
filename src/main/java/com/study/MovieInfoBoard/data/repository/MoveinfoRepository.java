package com.study.MovieInfoBoard.data.repository;

import com.study.MovieInfoBoard.data.entity.MovieinfoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface MoveinfoRepository extends JpaRepository<MovieinfoEntity,Integer> {

    /* Views Counting */
    @Modifying
    @Query(value = "update movieinfo p set p.hit = p.hit + 1 where p.id = :id",nativeQuery = true)
    int updateView(Integer id);
}
