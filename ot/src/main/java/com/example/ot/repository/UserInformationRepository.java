package com.example.ot.repository;

import com.example.ot.repository.entity.UserComment;
import com.example.ot.repository.entity.UserInformation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserInformationRepository extends JpaRepository<UserInformation, Integer> {
    @Query( value = "SELECT " +
            "users.id AS id, " +
            "users.account AS account, " +
            "users.name AS name, " +
            "users.branch_id AS branch_id, " +
            "users.department_id AS department_id, " +
            "users.is_stopped AS is_stopped, " +
            "branches.name AS branch_name, " +
            "departments.name AS department_name " +
            "FROM users " +
            "INNER JOIN branches " +
            "ON users.branch_id = branches.id " +
            "INNER JOIN departments " +
            "ON users.department_id = departments.id ",
            nativeQuery = true)
    public List<UserInformation> findAllUserInformationByOrderByCreatedDate();
}
