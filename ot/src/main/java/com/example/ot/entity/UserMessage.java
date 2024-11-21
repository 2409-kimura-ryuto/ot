package com.example.ot.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class UserMessage {
    // read onlyのentity
    private int id;
    private String account;
    private String name;
    private int userId;
    private String title;
    private String text;
    private String category;
    private Date createdDate;
    private Date updatedDate;

}
