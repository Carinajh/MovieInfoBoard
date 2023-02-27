package com.study.MovieInfoBoard.service;

import com.study.MovieInfoBoard.data.entity.MoviecommentEntity;
import java.util.List;

public interface MoviecommentService {
    List<MoviecommentEntity> findByInfoid(int infoid);

    long countByInfoid(int infoid);

    void saveComment(MoviecommentEntity moviecommentEntity);


}
