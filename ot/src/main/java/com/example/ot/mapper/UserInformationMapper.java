package com.example.ot.mapper;

import com.example.ot.entity.UserInformation;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserInformationMapper {
    public List<UserInformation> findAllUserInformationByOrderById();
}
