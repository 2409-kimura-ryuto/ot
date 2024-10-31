package com.example.ot.controller.form;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class UserCommentForm {
    private int id;
    private String account;
    private String name;
    private int userId;
    private int messageId;
    private String text;
    private String category;
    private Date createdDate;
    private Date updatedDate;
}
