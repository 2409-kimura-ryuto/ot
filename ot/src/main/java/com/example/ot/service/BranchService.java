package com.example.ot.service;

import com.example.ot.controller.form.BranchForm;
import com.example.ot.mapper.BranchMapper;
import com.example.ot.entity.Branch;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BranchService {

    @Autowired
    BranchMapper branchMapper;

    /*
     * DBからデータを取得
     */
    public List<BranchForm> findAll() {
        List<Branch> results = branchMapper.findAll();
        List<BranchForm> branches = setBranchForm(results);
        return branches;
    }
    /*
     * DBから取得したデータをFormに設定
     */
    private List<BranchForm> setBranchForm(List<Branch> results) {
        List<BranchForm> branches = new ArrayList<>();

        for (Branch result : results) {
            BranchForm branch = new BranchForm();
            BeanUtils.copyProperties(result, branch);
            branches.add(branch);
        }
        return branches;
    }
}
