package com.example.ot.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class Department {
    /*
     * id| name
     * --------------
     *  1| 総務人事部
     *  2| 情報管理部
     *  3| 営業部
     *  4| 技術部
     */

    private int id;
    private String name;
    private Date createdDate;
    private Date updatedDate;

}
