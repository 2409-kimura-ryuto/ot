package com.example.ot.service;

import com.example.ot.controller.form.CommentForm;
import com.example.ot.controller.form.UserCommentForm;
import com.example.ot.mapper.UserCommentMapper;
import com.example.ot.mapper.CommentMapper;
import com.example.ot.entity.Comment;
import com.example.ot.entity.UserComment;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CommentService {

    @Autowired
    UserCommentMapper userCommentMapper;
    @Autowired
    CommentMapper commentMapper;

    /*
     * UserCommentを取得
     */
    public List<UserCommentForm> findAllUserComment() {
        List<UserComment> results = userCommentMapper.findAllUserComment();
        List<UserCommentForm> comments = setUserCommentForm(results);
        return comments;
    }
    /*
     * DBから取得したUserCommentをFormに変換
     */
    private List<UserCommentForm> setUserCommentForm(List<UserComment> results) {
        List<UserCommentForm> messages = new ArrayList<>();

        for (UserComment result : results) {
            UserCommentForm comment = new UserCommentForm();
            BeanUtils.copyProperties(result, comment);
            messages.add(comment);
        }
        return messages;
    }

    /*
     * レコード追加
     */
    public void saveComment(CommentForm reqComment) {
        Comment saveComment = setCommentsEntity(reqComment);
        commentMapper.saveComment(saveComment);
    }

    /*
     *リストから取得した情報をentityに設定
     */
    private Comment setCommentsEntity(CommentForm reqComment) {
        Comment comment = new Comment();
        BeanUtils.copyProperties(reqComment, comment);
        return comment;
    }

    /*
     * レコード削除
     */
    public void deleteComment(Integer id) {
        commentMapper.deleteById(id);
    }
}
