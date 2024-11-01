package com.example.ot.controller.form;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserInformationForm {
    private int id;
    private String account;
    private String name;
    private int branchId;
    private int departmentId;
    private int isStopped;
    private String branchName;
    private String departmentName;
}
