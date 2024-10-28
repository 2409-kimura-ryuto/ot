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

    @Column
    private String account;

    @Column
    private String password;

    @Column
    private String name;

    @Column(name="branch_id")
    private int branchId;

    @Column(name="department_id")
    private int departmentId;

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
    }

    @PreUpdate
    public  void  onPreUpdate () {
        Date date = new Date();
        this .setUpdatedDate(date);
    }
}
