package com.example.ot.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class Branch {
    /*
     * id| name
     * --------------
     *  1| 本社
     *  2| A社
     *  3| B社
     *  4| C社
     */

    private int id;
    private String name;
    private Date createdDate;
    private Date updatedDate;

}
