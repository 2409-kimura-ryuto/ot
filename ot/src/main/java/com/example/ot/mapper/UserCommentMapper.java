package com.example.ot.mapper;

import com.example.ot.entity.UserComment;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserCommentMapper {
    public List<UserComment> findAllUserComment();
}
