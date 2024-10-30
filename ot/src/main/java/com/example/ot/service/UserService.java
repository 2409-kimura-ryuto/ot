package com.example.ot.service;

import com.example.ot.controller.form.MessageForm;
import com.example.ot.repository.entity.Message;
import com.example.ot.controller.form.UserCommentForm;
import com.example.ot.controller.form.UserInformationForm;
import com.example.ot.repository.UserInformationRepository;
import com.example.ot.repository.entity.UserComment;
import com.example.ot.repository.entity.UserInformation;
import org.springframework.stereotype.Service;

import com.example.ot.controller.form.UserForm;
import com.example.ot.repository.UserRepository;
import com.example.ot.repository.entity.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static com.example.ot.utils.HashUtil.hashWithSHA256;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserInformationRepository userInformationRepository;

    /*
     * ユーザー情取得処理(ログイン時に使用)
     */
    public UserForm findUser(UserForm userForm) throws Exception {
        //パスワードの暗号化
        String encryptPassword = hashWithSHA256(userForm.getPassword());
        User result = (User) userRepository.findByAccountAndPassword(
                userForm.getAccount(),
                encryptPassword);
        UserForm userFormResult = setUserForm(result);
        return userFormResult;
    }
    /*
     * ユーザ情取得処理(ユーザ登録時に使用)
     */
    public List<UserForm> findByAccount(String account) {
        // 内容修正 (リポジトリからの戻り値をリストからUserに変更、バリデーション通るか要確認)
        User result = userRepository.findAllByAccount(account);
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
        User result = (User)userRepository.findById(id).orElse(null);
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
        List<UserInformation> results = userInformationRepository.findAllUserInformationByOrderByCreatedDate();
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
        //開始・停止状態を更新する際にpasswordがnullでも動作させるための条件分岐
        if (reqUser.getPassword() != null) {
            String encryptPassword = hashWithSHA256(reqUser.getPassword());
            saveUser.setPassword(encryptPassword);
        }
        userRepository.save(saveUser);
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
