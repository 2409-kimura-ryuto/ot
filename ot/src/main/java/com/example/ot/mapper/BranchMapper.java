package com.example.ot.mapper;

import com.example.ot.entity.Branch;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BranchMapper {
    public List<Branch> findAll();
}
