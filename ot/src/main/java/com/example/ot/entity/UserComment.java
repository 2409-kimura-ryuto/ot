package com.example.ot.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class UserComment {
    // read onlyのentity
    private int id;
    private String account;
    private String name;
    private int userId;
    private int messageId;
    private String text;
    private Date createdDate;
    private Date updatedDate;

}
