package com.study.MovieInfoBoard.data.entity;


import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Table(name = "movieinfo")
@EntityListeners(AuditingEntityListener.class)
public class MovieinfoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    String title;
    String content;
    String filename;
    String filepath;
    int hit;
    String actor;
    char delflg;
    String userid;
    @CreatedDate
    @Column(updatable = false)
    Date created;
    @LastModifiedDate
    Date updated;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    Date openingdate;

}
