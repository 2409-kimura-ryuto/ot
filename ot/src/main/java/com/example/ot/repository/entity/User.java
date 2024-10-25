package com.example.ot.repository.entity;

<<<<<<< HEAD
import jakarta.persistence.Entity;

=======
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "users")
@Getter
@Setter
>>>>>>> 80c237b3e7b3488f16f6431bd802e60f61c82644
public class User {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String account;

    @Column
    private String password;

    @Column
    private String name;

    @Column(name="branch_id")
    private int branchId;

    @Column(name="department_id")
    private int departmentId;

    @Column(name="is_stopped")
    private int isStopped;

    @Column(name="created_date", insertable = false, updatable = false)
    private Date createdDate;

    @Column(name="updated_date", insertable = false, updatable = true)
    private Date updatedDate;
}
