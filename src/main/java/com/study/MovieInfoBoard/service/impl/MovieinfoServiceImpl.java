package com.study.MovieInfoBoard.service.impl;

import com.study.MovieInfoBoard.data.entity.MovieinfoEntity;
import com.study.MovieInfoBoard.data.repository.MovienfoRepository;
import com.study.MovieInfoBoard.service.MovieinfoService;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
public class MovieinfoServiceImpl implements MovieinfoService {

    Logger LOGGER = LoggerFactory.getLogger(MovieinfoService.class);
    private MovienfoRepository movienfoRepository;

    @Autowired
    public MovieinfoServiceImpl(MovienfoRepository movienfoRepository) {
        this.movienfoRepository = movienfoRepository;
    }

    @Override
    public List<MovieinfoEntity> getMovieInfoList() {
        LOGGER.info("[getMovieInfoList] 호출");
        return movienfoRepository.findAll();
    }

    @Override
    public MovieinfoEntity getMovieInfoView(Integer id) {
        LOGGER.info("[getMovieInfoView] 호출 : id = {}",id);
        return movienfoRepository.findById(id).get();
    }

    @Override
    public void write(MovieinfoEntity movieinfoEntity, MultipartFile multipartFile) {
        LOGGER.info("[write] 호출 ");
        String filesavepath = System.getProperty("user.dir")+"\\src\\main\\resources\\static\\files"; //프로젝트패스
        UUID uuid = UUID.randomUUID(); //랜덤식별자이름 생성.
        String filename = uuid + "_" + multipartFile.getOriginalFilename(); //랜덤 식별자 이름 + 파일명

        File saveFile= new File(filesavepath,filename); //파일경로,이름지정
        try {
            multipartFile.transferTo(saveFile); //파일 저장
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        movieinfoEntity.setFilename(filename); //파일명 설정
        movieinfoEntity.setFilepath("/files/"+filename);//파일패스 설정


        movienfoRepository.save(movieinfoEntity);
    }


    /* Views Counting */
    @Transactional
    @Override
    public void updateView(Integer id) {

        LOGGER.info("[updateView] 호출 ");
        movienfoRepository.updateView(id);
    }

    @Override
    public List<MovieinfoEntity> listOpeningdateDesc() {
        LOGGER.info("[listOpeningdateDesc] 호출 ");
        return movienfoRepository.listOpeningdateDesc();
    }

    @Override
    public void deleteByMovieinfo(Integer id) {
        LOGGER.info("[deleteByMovieinfo] 호출 ");
        movienfoRepository.deleteById(id);
    }
}
