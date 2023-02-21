package com.study.MovieInfoBoard.data.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class MovieuserDto {

    private int id;
    private String userid;
    private String userpw;
    private String name;
    private String email;
}
