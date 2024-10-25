package com.example.ot.controller.form;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class UserForm {
    private int id;

    @NotBlank(message = "アカウントを入力してください")
    private String account;

    @NotBlank(message = "パスワードを入力してください")
    private String password;
    private String name;
    private int branchId;
    private int departmentId;
    private int isStopped;
    private Date createdDate;
    private Date updatedDate;
}
