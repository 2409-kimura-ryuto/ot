package com.example.ot.repository;

<<<<<<< HEAD
import com.example.ot.repository.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
=======
import com.example.ot.controller.form.UserForm;
import com.example.ot.repository.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    @Query(
            value = "SELECT * FROM users " +
                    "WHERE account = :account " +
                    "AND password = :password " ,
            nativeQuery = true
    )
    public List<User> findAllByAccountAndPassword(@Param("account") String account, @Param("password")String password);
>>>>>>> 80c237b3e7b3488f16f6431bd802e60f61c82644
}
