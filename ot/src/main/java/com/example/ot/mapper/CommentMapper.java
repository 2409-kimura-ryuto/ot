package com.example.ot.mapper;

import com.example.ot.entity.Comment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface CommentMapper {
    public void saveComment(@Param("comment") Comment comment);
    public void deleteById(@Param("id") Integer id);
}
