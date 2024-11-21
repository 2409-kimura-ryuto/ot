package com.example.ot.entity;

import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.Getter;
import lombok.Setter;
import java.util.Date;

@Getter
@Setter
public class Comment {
    private Integer id;
    private String text;
    private int userId;
    private int messageId;
    private Date createdDate;
    private Date updatedDate;

    @PrePersist
    public  void  onPrePersist () {
        Date date = new Date();
        this .setCreatedDate(date);
        this .setUpdatedDate(date);
    }

    @PreUpdate
    public  void  onPreUpdate () {
        Date date = new Date();
        this .setUpdatedDate(date);
    }

}
