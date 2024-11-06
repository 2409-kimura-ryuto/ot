package com.example.ot.controller.form;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class UserMessageForm {
    private int id;
    private String account;
    private String name;
    private int userId;
    private String title;
    private String text;
    private String category;
    private Date createdDate;
    private Date updatedDate;

    // トップ画面の表示用
    private String postDate;
}
