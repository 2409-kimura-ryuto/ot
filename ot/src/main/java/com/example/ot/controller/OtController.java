package com.example.ot.controller;

import com.example.ot.controller.form.*;
import com.example.ot.repository.entity.Comment;
import com.example.ot.service.CommentService;
import com.example.ot.service.MessageService;
import com.example.ot.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.servlet.http.HttpSession;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
       // タスクデータオブジェクトを保管
//        mav.addObject("tasks", TasksData);
        return mav;
    }
    /*
     * ログイン処理
     */
    @PostMapping("/login")
    public ModelAndView login(@Validated @ModelAttribute("userForm") UserForm userForm, BindingResult result, RedirectAttributes redirectAttributes) throws Exception {
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

        // 画面遷移先を指定
        mav.setViewName("/login");

        //ログイン情報をセッションに格納
        session.setAttribute("user", userData);
        //top画面にリダイレクト 今はログイン
        FilterForm filterForm = new FilterForm();
        session.setAttribute("filterForm", filterForm);
        mav.setViewName("redirect:/top");
        return mav;
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
    @GetMapping("/top")
    public ModelAndView top(@ModelAttribute("filterForm") FilterForm filterForm) {

        // セッションからログインユーザ情報を取得する
        // ログインユーザが総務人事部の場合か判定 & フラグの設定
        // ログインユーザのIDを事前に決めておく必要あり→要相談

        ModelAndView mav = new ModelAndView();
        mav.setViewName("/top");

        List<UserMessageForm> messages = messageService.findAllUserMessage(filterForm.getStart(), filterForm.getEnd(), filterForm.getCategory());
        List<UserCommentForm> comments = commentService.findAllUserComment();

        mav.addObject("messages", messages);
        mav.addObject("comments", comments);
        mav.addObject("filterForm", filterForm);
        Comment emptyComment = new Comment();
        mav.addObject("emptyComment", emptyComment);

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
}