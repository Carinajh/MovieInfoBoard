package com.study.MovieInfoBoard.data.repository;

import com.study.MovieInfoBoard.data.entity.MovieinfoEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface MovienfoRepository extends JpaRepository<MovieinfoEntity,Integer> {

    /* Views Counting */
    @Modifying
    @Query(value = "update movieinfo p set p.hit = p.hit + 1 where p.id = :id",nativeQuery = true)
    int updateView(Integer id);

    @Query(value = "select * from movieinfo p order by p.openingdate desc",nativeQuery = true)
    List<MovieinfoEntity> listOpeningdateDesc();

    void deleteById(Integer id);
}
