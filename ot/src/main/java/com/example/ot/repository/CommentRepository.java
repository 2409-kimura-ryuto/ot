package com.example.ot.repository;

import com.example.ot.repository.entity.Comment;
import com.example.ot.repository.entity.UserComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {
    // postgreSQLにて動作確認済み
    @Query( value = "SELECT " +
            "comments.id AS id, " +
            "users.account AS account, " +
            "users.id AS userId, " +
            "comments.message_id AS messageId, " +
            "comments.text AS text, " +
            "comments.created_date AS created_date, " +
            "comments.updated_date AS updated_date " +
            "FROM comments " +
            "INNER JOIN users " +
            "ON comments.user_id = users.id " +
            "ORDER BY comments.created_date DESC",
            nativeQuery = true)
    public List<UserComment> findAllUserComment();
}
