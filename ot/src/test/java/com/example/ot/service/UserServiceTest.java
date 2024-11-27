package com.example.ot.service;

import com.example.ot.controller.form.UserForm;
import com.example.ot.controller.form.UserInformationForm;
import org.dbunit.IDatabaseTester;
import org.dbunit.JdbcDatabaseTester;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class UserServiceTest {

    @Autowired
    private UserService userService;

    private IDatabaseTester databaseTester;
    private IDataSet originalDataSet;

    //DB設定メソッド
    public void setting() throws ClassNotFoundException {
        //PostgreSQL用のDBUnitテスターを初期化
        databaseTester = new JdbcDatabaseTester(
                "org.postgresql.Driver",
                "jdbc:postgresql://localhost:5432/ot",
                "ot",
                "ot"
        );
    }

    @BeforeEach
    void setUp() throws Exception {
        //元のデータをバックアップ
        originalDataSet = databaseTester.getConnection().createDataSet();
    }

    @AfterEach
    void tearDown() throws Exception {
        //テスト後に元のデータを復元
        if (originalDataSet != null) {
            //ここでデータ復元処理を行う
            databaseTester.setDataSet(originalDataSet);
            databaseTester.setTearDownOperation(DatabaseOperation.CLEAN_INSERT); //元の状態に戻す
            databaseTester.onTearDown();
            databaseTester.getConnection().close();
        }
    }

    @Test
    void testFindByAccount() throws Exception{
        //DBの設定メソッドを呼び出し
        setting();

        //XMLデータセットを読み込む。
        IDataSet dataSet = new FlatXmlDataSetBuilder()
                .build(getClass().getClassLoader().getResourceAsStream("data/user-dataset.xml"));

        //DBUnitでデータを挿入
        databaseTester.setDataSet(dataSet);
        databaseTester.setSetUpOperation(DatabaseOperation.CLEAN_INSERT);
        databaseTester.setTearDownOperation(DatabaseOperation.DELETE_ALL);
        databaseTester.onSetup();

        //Serviceクラスのメソッドを呼び出し
        String findAccount = "testAccount1";
        List<UserForm> userForms = userService.findByAccount(findAccount);

        //結果を検証
        assertNotNull(userForms);
        assertEquals(1, userForms.size());

        //個別のデータを検証
        assertEquals("testAccount1", userForms.get(0).getAccount());
        assertEquals("testName1", userForms.get(0).getName());
    }

    @Test
    void testFindById() throws Exception {
        //DBの設定メソッドを呼び出し
        setting();

        //XMLデータセットを読み込む。
        IDataSet dataSet = new FlatXmlDataSetBuilder()
                .build(getClass().getClassLoader().getResourceAsStream("data/user-dataset.xml"));

        //DBUnitでデータを挿入
        databaseTester.setDataSet(dataSet);
        databaseTester.setSetUpOperation(DatabaseOperation.CLEAN_INSERT);
        databaseTester.setTearDownOperation(DatabaseOperation.DELETE_ALL);
        databaseTester.onSetup();

        //Serviceクラスのメソッドを呼び出し
        Integer findId = 2;
        UserForm userForm = userService.findById(findId);

        //結果を検証
        assertNotNull(userForm );

        //個別のデータを検証
        assertEquals("testAccount2", userForm.getAccount());
        assertEquals("testName2", userForm.getName());
    }

    @Test
    void testFindAllUserInformation() throws Exception {
        //DBの設定メソッドを呼び出し
        setting();

        //XMLデータセットを読み込む。
        IDataSet dataSet = new FlatXmlDataSetBuilder()
                .build(getClass().getClassLoader().getResourceAsStream("data/userInformation-dataset.xml"));

        //DBUnitでデータを挿入
        databaseTester.setDataSet(dataSet);
        databaseTester.setSetUpOperation(DatabaseOperation.CLEAN_INSERT);
        databaseTester.setTearDownOperation(DatabaseOperation.DELETE_ALL);
        databaseTester.onSetup();

        //Serviceクラスのメソッドを呼び出し
        List<UserInformationForm> userInformationForms = userService.findAllUserInformation();

        //結果を検証
        assertNotNull(userInformationForms);
        assertEquals(2, userInformationForms.size());

        //個別のデータを検証
        UserInformationForm userFirst = userInformationForms.get(0);
        assertEquals("testAccount1", userFirst.getAccount());
        assertEquals("testName1", userFirst.getName());
        assertEquals("本社", userFirst.getBranchName());
        assertEquals("情報管理部", userFirst.getDepartmentName());

        UserInformationForm userSecond = userInformationForms.get(1);
        assertEquals("testAccount1", userSecond.getAccount());
        assertEquals("testName1", userSecond.getName());
        assertEquals("B社", userSecond.getBranchName());
        assertEquals("営業部", userSecond.getDepartmentName());
    }
}