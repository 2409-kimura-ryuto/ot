package com.example.ot.controller.form;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.Map;

import static java.util.Map.entry;

@Getter
@Setter
public class BranchForm {
    private int id;
    private String name;
    private Date createdDate;
    private Date updatedDate;
}
