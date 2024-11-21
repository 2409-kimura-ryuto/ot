package com.example.ot.service;

import com.example.ot.controller.form.UserInformationForm;
import com.example.ot.mapper.UserInformationMapper;
import com.example.ot.entity.UserInformation;
import org.springframework.stereotype.Service;

import com.example.ot.controller.form.UserForm;
import com.example.ot.mapper.UserMapper;
import com.example.ot.entity.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

import static com.example.ot.utils.HashUtil.hashWithSHA256;

@Service
public class UserService {

    @Autowired
    UserMapper userMapper;

    @Autowired
    UserInformationMapper userInformationMapper;

    /*
     * ユーザー情取得処理(ログイン時に使用)
     */
    public UserForm findUser(UserForm userForm) throws Exception {
        //パスワードの暗号化
        String encryptPassword = hashWithSHA256(userForm.getPassword());
        User result = (User) userMapper.findByAccountAndPassword(
                userForm.getAccount(),
                encryptPassword);
        UserForm userFormResult = setUserForm(result);
        return userFormResult;
    }
    /*
     * ユーザ情取得処理(ユーザ登録時に使用)
     */
    public List<UserForm> findByAccount(String account) {
        User result = userMapper.findAllByAccount(account);
        List<UserForm> users = new ArrayList<>();

        if (result == null) {
            return users;
        }
        UserForm user = setUserForm(result);
        users.add(user);
        return users;
    }
    /*
     * ユーザ情取得処理
     */
    public UserForm findById(Integer id) {
        User result = (User)userMapper.findAllById(id);
        UserForm user = setUserForm(result);
        return user;
    }

    /*
     * DBから取得したデータをFormに設定
     */
    private UserForm setUserForm(User result) {
        UserForm userForm = new UserForm();
        BeanUtils.copyProperties(result, userForm);
        return  userForm;
    }

    /*
     * UserInformationを全件取得
     */
    public List<UserInformationForm> findAllUserInformation() {
        List<UserInformation> results = userInformationMapper.findAllUserInformationByOrderById();
        List<UserInformationForm> userInformations = setUserInformationForm(results);
        return userInformations;
    }
    /*
     * DBから取得したUserInformationをFormに変換
     */
    private List<UserInformationForm> setUserInformationForm(List<UserInformation> results) {
        List<UserInformationForm> userInformations = new ArrayList<>();

        for (UserInformation result : results) {
            UserInformationForm userInformation = new UserInformationForm();
            BeanUtils.copyProperties(result, userInformation);
            userInformations.add(userInformation);
        }
        return userInformations;
    }

    /*
     * レコード追加
     */
    public void saveUser(UserForm reqUser) throws Exception {
        User saveUser = setUserEntity(reqUser);
        // passwordがnullまたはBlankの場合は既存のPWをDBから取得しセットする
        if (reqUser.getPassword() == null || reqUser.getPassword().isBlank()) {
            UserForm refUser = findById(reqUser.getId());
            saveUser.setPassword(refUser.getPassword());
        } else {
            String encryptPassword = hashWithSHA256(reqUser.getPassword());
            saveUser.setPassword(encryptPassword);
        }
        userMapper.saveUser(saveUser);
    }

    /*
     * 停止状態更新
     */
    public void changeIsStopped(UserForm reqUser, Integer id) throws Exception {
        User saveUser = setUserEntity(reqUser);
        userMapper.changeIsStopped(saveUser, id);
    }

    /*
     * アカウント編集
     */
    public void updateUser(UserForm reqUser) throws Exception {
        User saveUser = setUserEntity(reqUser);
        userMapper.updateUser(saveUser);
    }

    /*
     * リクエストから取得した情報をentityに設定
     */
    private User setUserEntity(UserForm reqUser) {
        // Stringのbranch, departmentをintに変換する
        User user = new User();
        BeanUtils.copyProperties(reqUser, user);
        return user;
    }
}
