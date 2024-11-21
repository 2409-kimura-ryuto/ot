package com.example.ot.mapper;

import com.example.ot.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.repository.query.Param;

@Mapper
public interface UserMapper {
    public User findByAccountAndPassword(@Param("account") String account, @Param("password")String password);
    public User findAllByAccount(@Param("account") String account);
    public User findAllById(@Param("id") Integer id);
    public void saveUser(@Param("user") User user);
    public void changeIsStopped(@Param("user") User user, @Param("id") Integer id);
    public void updateUser(@Param("user") User user);
}
