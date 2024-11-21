package com.example.ot.mapper;

import com.example.ot.entity.Branch;
import com.example.ot.entity.Department;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DepartmentMapper {
    public List<Department> findAll();
}
