package com.study.MovieInfoBoard.data.repository;

import com.study.MovieInfoBoard.data.entity.MoviecommentEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MoviecommentRepository extends JpaRepository<MoviecommentEntity,Integer> {

    List<MoviecommentEntity> findByInfoid(int infoid);

    long countByInfoid(int infoid);

}
