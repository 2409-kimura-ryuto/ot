package com.example.ot.entity;

import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.Getter;
import lombok.Setter;
import java.util.Date;

@Getter
@Setter
public class Message {
    private int id;
    private String title;
    private String text;
    private String category;
    private int userId;
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
