package com.example.ot.repository.entity;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.Date;

@Entity
@Table(name = "branches")
@Getter
public class Branch {

    /*
     * id| name
     * --------------
     *  1| 本社
     *  2| A社
     *  3| B社
     *  4| C社
     */

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String name;

    @Column(name="created_date")
    private Date createdDate;

    @Column(name="updated_date")
    private Date updatedDate;
}
