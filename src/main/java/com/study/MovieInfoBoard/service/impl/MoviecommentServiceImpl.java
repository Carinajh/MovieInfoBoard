package com.study.MovieInfoBoard.service.impl;

import com.study.MovieInfoBoard.data.entity.MoviecommentEntity;
import com.study.MovieInfoBoard.data.repository.MoviecommentRepository;
import com.study.MovieInfoBoard.data.repository.MovieuserRepository;
import com.study.MovieInfoBoard.service.MoviecommentService;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MoviecommentServiceImpl implements MoviecommentService {

    Logger LOGGER = LoggerFactory.getLogger(MoveuserServiceImpl.class);

    @Autowired
    private MoviecommentRepository moviecommentRepository;

    public MoviecommentServiceImpl(MoviecommentRepository moviecommentRepository) {
        this.moviecommentRepository = moviecommentRepository;
    }

    @Override
    public List<MoviecommentEntity> findByInfoid(int infoid) {
        LOGGER.info("[MoviecommentServiceImpl] findByInfoid 호출");
        return moviecommentRepository.findByInfoid(infoid);
    }

    @Override
    public long countByInfoid(int infoid) {
        LOGGER.info("[MoviecommentServiceImpl] countByInfoid 호출");
        return moviecommentRepository.countByInfoid(infoid);
    }

    public void saveComment(MoviecommentEntity moviecommentEntity){
        LOGGER.info("[MoviecommentServiceImpl] saveComment 호출");
        moviecommentRepository.save(moviecommentEntity);
    }

    @Override
    public void deletebycommentid(Integer id) {
        LOGGER.info("[MoviecommentServiceImpl] deletebycommentid 호출");
        moviecommentRepository.deleteById(id);
    }

}
