package com.example.ot.service;

import org.springframework.stereotype.Service;

import com.example.ot.controller.form.UserForm;
import com.example.ot.repository.UserRepository;
import com.example.ot.repository.entity.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.example.ot.utils.CipherUtil.encrypt;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    /*
     * ユーザー情取得処理(ログイン時に使用)
     */
    public UserForm findUser(UserForm userForm) throws Exception {
        //パスワードの暗号化
        /* for test
        String encryptPassword = encrypt(userForm.getPassword());

        List<User> results = userRepository.findAllByAccountAndPassword(
                userForm.getAccount(),
                encryptPassword);
         */
//        List<User> results = userRepository.findAllByAccountAndPassword(
//                userForm.getAccount(),
//                userForm.getPassword());
//        List<UserForm> users = setUserForm(results);
//        return users;
        User result = (User) userRepository.findByAccountAndPassword(
                userForm.getAccount(),
                userForm.getPassword());
        UserForm userFormResult = setUserForm(result);
        return userFormResult;
    }

    /*
     * DBから取得したデータをFormに設定
     */
    private UserForm setUserForm(User result) {
        UserForm userForm = new UserForm();
        BeanUtils.copyProperties(result, userForm);
        return  userForm;
    }
}
