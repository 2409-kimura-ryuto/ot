package com.example.ot.service;

import com.example.ot.controller.form.DepartmentForm;
import com.example.ot.repository.DepartmentRepository;
import com.example.ot.repository.entity.Department;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DepartmentService {

    @Autowired
    DepartmentRepository departmentRepository;

    /*
     * DBからデータを取得
     */
    public List<DepartmentForm> findAll() {
        List<Department> results = departmentRepository.findAll();
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
