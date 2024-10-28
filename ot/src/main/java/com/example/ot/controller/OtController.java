package com.example.ot.controller;

import com.example.ot.controller.form.MessageForm;
import com.example.ot.controller.form.UserCommentForm;
import com.example.ot.controller.form.UserMessageForm;
import com.example.ot.controller.form.UserForm;
import com.example.ot.service.CommentService;
import com.example.ot.service.MessageService;
import com.example.ot.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;

import jakarta.servlet.http.HttpSession;

import java.util.List;
import java.util.ArrayList;

@Controller
public class OtController {

    @Autowired
    UserService userService;

    @Autowired
    MessageService messageService;

    @Autowired
    CommentService commentService;

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
        return mav;
    }
    /*
     * ログイン処理
     */
    @PostMapping("/login")
    public ModelAndView login(@Validated @ModelAttribute("userForm") UserForm userForm, BindingResult result) throws Exception {
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

        if ((userData.size() == 0) || (userData.get(0).getIsStopped() == 1)) {
            errorList.add("ログインに失敗しました");
            mav.addObject("validationError", errorList);
            mav.setViewName("/login");
            return mav;
        }

        //ログイン情報をセッションに格納
        session.setAttribute("user", userData);
        //topが画面が実装でき次第topにリダイレクトするように変更
        return new ModelAndView("redirect:/login");
    }

    /*
     * ログアウト処理
     */
    @GetMapping("logout")
    public ModelAndView logout() {
        // セッションの無効化
        session.invalidate();
        return new ModelAndView("redirect:/login");
    }

    /*
     * ホーム画面表示
     */
    @GetMapping("/{start}-{end}-{category}")
    public ModelAndView top() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("/top");

        List<UserMessageForm> messages = messageService.findAllUserMessage();
        List<UserCommentForm> comments = commentService.findAllUserComment();

        mav.addObject("messages", messages);
        mav.addObject("comments", comments);

        return mav;
    }

    /*
     * 新規投稿画面表示
     */
    @GetMapping("/new")
    public ModelAndView newMessage() {
        ModelAndView mav = new ModelAndView();
        MessageForm messageForm = new MessageForm();
        mav.addObject("messageForm", messageForm);
        mav.setViewName("/new");
        return mav;
    }

    /*
     * 投稿登録処理
     */
    @PostMapping("/add-Message")
    public ModelAndView addMessage(@Validated @ModelAttribute("messageForm") MessageForm messageForm, BindingResult result) {
        ModelAndView mav = new ModelAndView();
        //バリデーション処理
        if (result.hasErrors()) {
            mav.setViewName("/new");
            return mav;
        }
        messageService.saveMessage(messageForm);
        //topが画面が実装でき次第topにリダイレクトするように変更
        return new ModelAndView("redirect:/new");
    }

    /*
     * 投稿削除処理
     */
    @DeleteMapping("/delete-message/{id}")
    public ModelAndView deleteMessage(@PathVariable Integer id) {
        // 投稿をテーブルから削除
        messageService.deleteMessage(id);
        // rootへリダイレクト
        return new ModelAndView("redirect:/top");
    }

}