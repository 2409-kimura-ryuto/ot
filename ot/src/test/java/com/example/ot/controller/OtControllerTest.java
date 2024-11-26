package com.example.ot.controller;

import com.example.ot.controller.form.UserForm;
import com.example.ot.repository.entity.User;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class OtControllerTest {

    @BeforeEach
    void setUp() {
        System.out.println("Before Each");
    }

    @AfterEach
    void tearDown() {
        System.out.println("After Each");
    }

    private final OtController otController = new OtController();

    //①-1
    @Tag("userTest")
    @Order(2)
    @Test
    void testUser_SizeZero() {
        List<User> users = new ArrayList<>();
        UserForm userForm = new UserForm();
        List<String> errorList = otController.userTest(users, userForm);
        //usersの要素が0で、エラーメッセージの要素も0であることを確認
        assertEquals(0, users.size());
        assertTrue(errorList.isEmpty());

    }

    //①-2
    @DisplayName("最初に行うテスト")
    @Tag("userTest")
    @Order(1)
    @Test
    void testUser_IdDisMatch() {
        List<User> users = new ArrayList<>();
        User user = new User();
        UserForm userForm = new UserForm();
        //userに値をセットし、usersに追加
        user.setId(1);
        user.setName("taro");
        users.add(user);
        //userFormにidをセット
        userForm.setId(2);
        //usersの要素が1であるが、エラーメッセージの要素は0であることを確認
        List<String> errorList = otController.userTest(users, userForm);
        assertEquals(1, users.size());
        assertEquals("アカウントが重複しています", errorList.get(0));
    }

    //①-3
    @Tag("userTest")
    @Order(4)
    @Test
    void testUser_True() {
        List<User> users = new ArrayList<>();
        User user = new User();
        UserForm userForm = new UserForm();
        //userに値をセットし、usersに追加
        user.setId(1);
        user.setName("taro");
        users.add(user);
        //userFormにidをセット
        userForm.setId(1);
        //usersの要素が1で、アカウント重複のエラーメッセージが格納されることを確認
        List<String> errorList = otController.userTest(users, userForm);
        assertEquals(1, users.size());
        assertTrue(errorList.isEmpty());
    }

    //②-1
    @Tag("passwordTest")
    @Order(5)
    @Test
    void testPassword_Blank() {
        UserForm userForm = new UserForm();
        //userFormに空文字をセット
        userForm.setPassword("");
        //userForm.getPassword()がblankであることと、errorListの要素数が0であることを確認
        List<String> errorList = otController.passwordTest(userForm);
        assertTrue(userForm.getPassword().isBlank());
        assertTrue(errorList.isEmpty());
    }

    //②-2
    @Tag("passwordTest")
    @Order(6)
    @Test
    void testPassword_NotBlankMatch() {
        UserForm userForm = new UserForm();
        //userFormに正規表現にマッチするpasswordをセット
        userForm.setPassword("testPassword");
        //userForm.getPassword()が存在するが、errorListの要素数が0であることを確認
        List<String> errorList = otController.passwordTest(userForm);
        assertEquals("testPassword", userForm.getPassword());
        assertTrue(errorList.isEmpty());
    }

    //②-3
    @Tag("passwordTest")
    @Order(7)
    @Test
    void testPassword_NotBlankDisMatchUnder() {
        UserForm userForm = new UserForm();
        //userFormに正規表現にマッチしないpasswordをセット(5文字以下)
        userForm.setPassword("test");
        //userForm.getPassword()が存在するが、errorListにエラーメッセージが追加されていることを確認
        List<String> errorList = otController.passwordTest(userForm);
        assertEquals("test", userForm.getPassword());
        assertEquals("パスワードは半角文字かつ6文字以上20文字以下で入力してください", errorList.get(0));
    }

    //②-4
    @Tag("passwordTest")
    @Order(3)
    @Test
    void testPassword_NotBlankDisMatchOver() {
        UserForm userForm = new UserForm();
        //userFormに正規表現にマッチしないpasswordをセット(21文字以上)
        userForm.setPassword("testPasswordTestPassword");
        //userForm.getPassword()が存在するが、errorListにエラーメッセージが追加されていることを確認
        List<String> errorList = otController.passwordTest(userForm);
        assertEquals("testPasswordTestPassword", userForm.getPassword());
        assertEquals("パスワードは半角文字かつ6文字以上20文字以下で入力してください", errorList.get(0));
    }

    //②-5
    @DisplayName("最後に行うテスト")
    @Tag("passwordTest")
    @Order(8)
    @Test
    void testPassword_NotBlankDisMatchFormat() {
        UserForm userForm = new UserForm();
        //userFormに正規表現にマッチしないpasswordをセット(フォーマットのみが異常系)
        userForm.setPassword("テストパスワード");
        //userForm.getPassword()が存在するが、errorListにエラーメッセージが追加されていることを確認
        List<String> errorList = otController.passwordTest(userForm);
        assertEquals("テストパスワード", userForm.getPassword());
        assertEquals("パスワードは半角文字かつ6文字以上20文字以下で入力してください", errorList.get(0));
    }
}