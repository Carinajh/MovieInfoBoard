package com.study.MovieInfoBoard.data.dto;

import java.sql.Date;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MovieinfoDto {
    private int id;
    private String title;
    private String content;
    private String filename;
    private String filepath;
    private int hit;
    private String actor;
    private char delflg;
    private String userid;
    private Date created;
    private Date updated;
    private Date openingdate;

}
