package com.example.ot.repository.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "comments")
@Getter
@Setter
public class Comment {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String text;

    @Column
    private int userId;

    @Column
    private int messageId;

    @Column(name="created_date", insertable = true, updatable = false)
    private Date createdDate;

    @Column(name="updated_date", insertable = true, updatable = true)
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
