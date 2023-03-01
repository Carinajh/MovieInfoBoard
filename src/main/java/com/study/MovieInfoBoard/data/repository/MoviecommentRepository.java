package com.study.MovieInfoBoard.data.repository;

import com.study.MovieInfoBoard.data.entity.MoviecommentEntity;
import com.study.MovieInfoBoard.data.entity.MovieinfoEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MoviecommentRepository extends JpaRepository<MoviecommentEntity,Integer> {

    List<MoviecommentEntity> findByInfoid(int infoid);

    long countByInfoid(int infoid);

    void deleteById(Integer id);

}
