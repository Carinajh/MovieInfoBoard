package com.study.MovieInfoBoard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing	//작성 갱신시간 감시기능활성화
@SpringBootApplication
public class MovieInfoBoardApplication {

	public static void main(String[] args) {
		SpringApplication.run(MovieInfoBoardApplication.class, args);
	}

}
