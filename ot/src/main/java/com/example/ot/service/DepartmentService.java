package com.example.ot.service;

import com.example.ot.controller.form.DepartmentForm;
import com.example.ot.mapper.DepartmentMapper;
import com.example.ot.entity.Department;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DepartmentService {

    @Autowired
    DepartmentMapper departmentMapper;

    /*
     * DBからデータを取得
     */
    public List<DepartmentForm> findAll() {
        List<Department> results = departmentMapper.findAll();
        List<DepartmentForm> departments = setDepartmentForm(results);
        return departments;
    }
    /*
     * DBから取得したデータをFormに設定
     */
    private List<DepartmentForm> setDepartmentForm(List<Department> results) {
        List<DepartmentForm> departments = new ArrayList<>();

        for (Department result : results) {
            DepartmentForm department = new DepartmentForm();
            BeanUtils.copyProperties(result, department);
            departments.add(department);
        }
        return departments;
    }
}
