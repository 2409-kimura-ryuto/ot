package com.example.ot.repository;

import com.example.ot.repository.entity.UserMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface UserMessageRepository extends JpaRepository<UserMessage, Integer> {

    @Query( value = "SELECT " +
                    "messages.id AS id, " +
                    "users.account AS account, " +
                    "users.name AS name, " +
                    "users.id AS user_id, " +
                    "messages.title AS title, " +
                    "messages.text AS text, " +
                    "messages.category AS category, " +
                    "messages.created_date AS created_date, " +
                    "messages.updated_date AS updated_date " +
                    "FROM messages " +
                    "INNER JOIN users " +
                    "ON messages.user_id = users.id " +
                    "WHERE messages.created_date BETWEEN :start AND :end " +
                    "ORDER BY messages.created_date DESC",
            nativeQuery = true)
    public List<UserMessage> findAllUserMessage(@Param("start") Date start, @Param("end")Date end);

    @Query( value = "SELECT " +
                    "messages.id AS id, " +
                    "users.account AS account, " +
                    "users.name AS name, " +
                    "users.id AS user_id, " +
                    "messages.title AS title, " +
                    "messages.text AS text, " +
                    "messages.category AS category, " +
                    "messages.created_date AS created_date, " +
                    "messages.updated_date AS updated_date " +
                    "FROM messages " +
                    "INNER JOIN users " +
                    "ON messages.user_id = users.id " +
                    "WHERE messages.created_date BETWEEN :start AND :end AND category LIKE :category " +
                    "ORDER BY messages.created_date DESC;",
            nativeQuery = true)
    public List<UserMessage> findAllUserMessage(@Param("start") Date start, @Param("end")Date end, @Param("category")String category);
}