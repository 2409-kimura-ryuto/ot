package com.example.ot.repository.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class UserInformation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String account;
    private String name;

    @Column(name="branch_id")
    private int branchId;

    @Column(name="department_id")
    private int departmentId;

    @Column(name="is_stopped")
    private int isStopped;

    @Column(name="branch_name")
    private String branchName;

    @Column(name="department_name")
    private String departmentName;
}
