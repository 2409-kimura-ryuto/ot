package com.example.ot.service;

import com.example.ot.controller.form.UserForm;
import com.example.ot.repository.UserRepository;
import com.example.ot.repository.entity.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    @Autowired
    UserRepository taskRepository;

    /*
     * ユーザー情取得処理(ログイン時に使用)
     */
    public List<UserForm> findUser(UserForm userForm) {
        List<User> results = taskRepository.findAllByAccountAndPassword(
                userForm.getAccount(),
                userForm.getPassword());
        List<UserForm> users = setUserForm(results);
        return users;
    }

    /*
     * DBから取得したデータをFormに設定
     */
    private List<UserForm> setUserForm(List<User> results) {
        List<UserForm> users = new ArrayList<>();

        for (int i = 0; i < results.size(); i++) {
            UserForm User = new UserForm();
            User result = results.get(i);
            User.setId(result.getId());
            User.setAccount(result.getAccount());
            User.setPassword(result.getPassword());
            User.setName(result.getName());
            User.setBranchId(result.getBranchId());
            User.setDepartmentId(result.getDepartmentId());
            users.add(User);
        }
        return users;
    }
}
