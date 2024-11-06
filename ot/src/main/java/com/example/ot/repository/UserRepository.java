package com.example.ot.repository;

import com.example.ot.repository.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    @Query(
            value = "SELECT * FROM users " +
                    "WHERE account = :account " +
                    "AND password = :password " ,
            nativeQuery = true
    )
    public User findByAccountAndPassword(@Param("account") String account, @Param("password")String password);

    @Query(
            value = "SELECT * FROM users " +
                    "WHERE account = :account",
            nativeQuery = true
    )
    public User findAllByAccount(@Param("account") String account);

}
