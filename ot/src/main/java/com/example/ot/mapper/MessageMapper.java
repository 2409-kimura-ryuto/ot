package com.example.ot.mapper;

import com.example.ot.entity.Message;
import com.example.ot.entity.UserMessage;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.repository.query.Param;

import java.util.List;

@Mapper
public interface MessageMapper {
    public void saveMessage(@Param("message") Message message);
    public void deleteById(@Param("id") Integer id);
}
