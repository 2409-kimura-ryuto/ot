package com.example.ot.controller.form;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class DepartmentForm {
    private int id;
    private String name;
    private Date createdDate;
    private Date updatedDate;
}
