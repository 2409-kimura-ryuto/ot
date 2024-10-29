package com.example.ot.repository.entity;


import jakarta.persistence.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "users")
@Getter
@Setter
public class User {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(updatable = false)
    private String account;

    @Column(updatable = false)
    private String password;

    @Column(updatable = false)
    private String name;

    @Column(name="branch_id", updatable = false)
    private int branchId;

    @Column(name="department_id", updatable = false)
    private int departmentId;

    // 0: 運用中, 1: 停止中
    @Column(name="is_stopped")
    private int isStopped;

    @Column(name="created_date", insertable = true, updatable = false)
    private Date createdDate;

    @Column(name="updated_date", insertable = true, updatable = true)
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
