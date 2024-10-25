package com.example.ot.controller.form;

import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class UserMessageForm {
    private int id;
    private String account;
    private int userId;
    private String title;
    private String text;
    private String category;
    private Date createdDate;
    private Date updatedDate;
}
