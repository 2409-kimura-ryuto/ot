package com.example.ot.mapper;

import com.example.ot.entity.UserMessage;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;

@Mapper
public interface UserMessageMapper {
    public List<UserMessage> findAllUserMessage(@Param("start") Date start, @Param("end") Date end);
    public List<UserMessage> findAllUserMessageByCategory(@Param("start") Date start, @Param("end")Date end, @Param("category")String category);
}
