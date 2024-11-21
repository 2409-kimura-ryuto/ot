package com.example.ot.entity;

import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.Getter;
import lombok.Setter;
import java.util.Date;

@Getter
@Setter
public class User {
    private int id;
    private String account;
    private String password;
    private String name;
    private int branchId;
    private int departmentId;

    // 0: 運用中, 1: 停止中
    private int isStopped;
    private Date createdDate;
    private Date updatedDate;

    @PrePersist
    public  void  onPrePersist () {
        Date date = new Date();
        this .setCreatedDate(date);
        this .setUpdatedDate(date);
        this .setIsStopped(0);
    }

    @PreUpdate
    public  void  onPreUpdate () {
        Date date = new Date();
        this .setUpdatedDate(date);
    }

}
