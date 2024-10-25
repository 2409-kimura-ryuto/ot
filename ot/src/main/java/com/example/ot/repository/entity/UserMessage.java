package com.example.ot.repository.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Getter
@Setter
public class UserMessage {
    // read onlyのentity
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String account;

    @Column
    private int userId;

    @Column
    private String title;

    @Column
    private String text;

    @Column
    private String category;

    @Column(name="created_date")
    private Date createdDate;

    @Column(name="updated_date")
    private Date updatedDate;

}
