package com.example.ot.controller;

import com.example.ot.controller.form.UserForm;
import com.example.ot.repository.entity.User;
import com.example.ot.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;

@Controller
public class OtController {

    @Autowired
    UserService userService;
    @Autowired
    HttpSession session;

    /*
     * ログイン画面表示処理
     */
    @GetMapping("/login")
    public ModelAndView loginTop() {
        ModelAndView mav = new ModelAndView();
        // form用の空のentityを準備
        UserForm userForm = new UserForm();
        // 画面遷移先を指定
        mav.setViewName("/login");
        // 準備した空のFormを保管
        mav.addObject("userForm", userForm);
       // タスクデータオブジェクトを保管
//        mav.addObject("tasks", TasksData);
        return mav;
    }

    /*
     * ログイン処理
     */
    @PostMapping("/login")
    public ModelAndView login(@Validated @ModelAttribute("userForm") UserForm userForm, BindingResult result) {
        ModelAndView mav = new ModelAndView();

        //バリデーション処理
        List<String> errorList = new ArrayList<String>();
        if (result.hasErrors()) {
            for (ObjectError error : result.getAllErrors()) {
                errorList.add(error.getDefaultMessage());
            }

            mav.addObject("validationError", errorList);
            mav.setViewName("/login");
            return mav;
        }

        List<UserForm> userData = userService.findUser(userForm);

        if ((userData == null) || (userData.getIsStopped() == 1)) {
            errorList.add("ログインに失敗しました");
            mav.addObject("validationError", errorList);
            mav.setViewName("/login");
            return mav;
        }

        // 画面遷移先を指定
        mav.setViewName("/login");

        // 投稿データオブジェクトを保管
//        mav.addObject("user", userData);
        //ログイン情報をセッションに格納
        session.setAttribute("user", userData);
        //top画面にリダイレクト 今はログイン
        return new ModelAndView("redirect:/login");
    }

}