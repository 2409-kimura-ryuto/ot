package com.example.ot.repository.entity;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.Date;

@Entity
@Table(name = "departments")
@Getter
public class Department {

    /*
     * id| name
     * --------------
     *  1| 総務人事部
     *  2| 情報管理部
     *  3| 営業部
     *  4| 技術部
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
