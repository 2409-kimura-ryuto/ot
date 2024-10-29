package com.example.ot.controller;

import com.example.ot.controller.form.*;
import com.example.ot.repository.entity.Comment;
import com.example.ot.repository.entity.User;
import com.example.ot.repository.entity.UserInformation;
import com.example.ot.service.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;

import jakarta.servlet.http.HttpSession;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.ArrayList;
import java.util.Objects;

import static com.example.ot.utils.CipherUtil.encrypt;

@Controller
public class OtController {

    @Autowired
    UserService userService;

    @Autowired
    MessageService messageService;

    @Autowired
    CommentService commentService;

    @Autowired
    BranchService branchService;

    @Autowired
    DepartmentService departmentService;

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
                // 特定のエラーを除外する
                if (!Objects.equals(error.getDefaultMessage(), "パスワードと確認用パスワードが一致しません") &&
                        !Objects.equals(error.getDefaultMessage(), "氏名を入力してください")) {
                    errorList.add(error.getDefaultMessage());
                }
            }
        }
        if (errorList.size() > 0) {
            mav.addObject("validationError", errorList);
            mav.setViewName("/login");
            return mav;
        }
        UserForm userData = null;
        try {
            userData = userService.findUser(userForm);
        } catch(RuntimeException e) {
            userData = null;
        }

        if ((userData == null) || (userData.getIsStopped() == 1)) {
            errorList.add("ログインに失敗しました");
            mav.addObject("validationError", errorList);
            mav.setViewName("/login");
            return mav;
        }

        // 画面遷移先を指定
        mav.setViewName("/login");

        //ログイン情報をセッションに格納
        session.setAttribute("user", userData);
        // 空の絞り込み条件をセット
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

        // ログインフィルター
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

    /*
     * 投稿登録処理
     */
    @PostMapping("/add-message")
    public ModelAndView addMessage(@Validated @ModelAttribute("messageForm") MessageForm messageForm, BindingResult result) {
        ModelAndView mav = new ModelAndView();
        //バリデーション処理
        if (result.hasErrors()) {
            mav.setViewName("/new");
            return mav;
        }

        UserForm userForm = (UserForm) session.getAttribute("user");
        messageForm.setUserId(userForm.getId());
        messageService.saveMessage(messageForm);
        return new ModelAndView("redirect:/top");
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
    /*
     * ユーザー登録画面表示
     */
    @GetMapping("/user-registration")
    public ModelAndView addUser(@ModelAttribute("user")UserForm loginUser) {

        ModelAndView mav = new ModelAndView();
        /* 管理者権限フィルター
        if (user.getDepartmentId() != 1) {
            List<String> errorList = new ArrayList<String>();
            errorList.add("無効なアクセスです");
            mav.addObject("validationError", errorList);
            mav.setViewName("/login");
        }
         */

        List<BranchForm> branches = branchService.findAll();
        mav.addObject("branches", branches);

        List<DepartmentForm> departments = departmentService.findAll();
        mav.addObject("departments", departments);

        UserForm userForm = new UserForm();
        mav.addObject("userForm", userForm);
        mav.setViewName("/user-registration");
        return mav;
    }
    /*
     * ユーザー登録処理
     */
    @PutMapping("/signup")
    public ModelAndView signup(@Validated @ModelAttribute("userForm")UserForm userForm, BindingResult result)  throws Exception {
        ModelAndView mav = new ModelAndView();

        // バリデーション処理
        List<String> errorList = new ArrayList<String>();
        if (result.hasErrors()) {
            for (ObjectError error : result.getAllErrors()) {
                errorList.add(error.getDefaultMessage());
            }
        }
        List<UserForm> users = userService.findByAccount(userForm.getAccount());
        if (users.size() > 0) {
            errorList.add("アカウントが重複しています");
        }
        if (!errorList.isEmpty()) {
            List<BranchForm> branches = branchService.findAll();
            mav.addObject("branches", branches);
            List<DepartmentForm> departments = departmentService.findAll();
            mav.addObject("departments", departments);
            mav.addObject("userForm", userForm);
            mav.addObject("validationError", errorList);
            mav.setViewName("/user-registration");
            return mav;
        }
        // 登録処理
        String encryptPassword = encrypt(userForm.getPassword());
        userService.saveUser(userForm);
        // ユーザ管理画面へリダイレクトに要修正
        mav.setViewName("redirect:/top");
        return mav;
    }
    /*
     * コメント登録処理
     */
    @PostMapping("/add-comment/{id}")
    public ModelAndView addComment(@PathVariable Integer id, @Validated @ModelAttribute("commentForm") CommentForm commentForm, BindingResult result) {
        ModelAndView mav = new ModelAndView();
        //バリデーション処理
        if (result.hasErrors()) {
            mav.setViewName("/top");
            return mav;
        }
        /*sessionからログインユーザーIDを取得しセット。messageIdはviewでcommentFormにセットする？
         ログインユーザーIDも出来ればviewでセットしたい*/
        UserForm userForm = (UserForm) session.getAttribute("user");
        commentForm.setUserId(userForm.getId());

        //commentIdセット
        commentForm.setMessageId(id);

        commentService.saveComment(commentForm);
        //topが画面が実装でき次第topにリダイレクトするように変更
        return new ModelAndView("redirect:/top");
    }

    /*
     * コメント削除処理
     */
    @DeleteMapping("/delete-comment/{id}")
    public ModelAndView deleteComment(@PathVariable Integer id) {
        // 投稿をテーブルから削除
        commentService.deleteComment(id);
        // rootへリダイレクト
        return new ModelAndView("redirect:/top");
    }

    /*
     * ユーザー管理画面表示処理
     */
    @GetMapping("/user-management")
    public ModelAndView userManagement() {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("/user-management");

        List<UserInformationForm> userInformations = userService.findAllUserInformation();
        mav.addObject("users", userInformations);
        return mav;
    }

    /*
     * ユーザー復活停止状態更新処理
     */
    @PostMapping("/change-isStopped/{id}")
    public ModelAndView changeIsStopped(@ModelAttribute("UserForm") UserForm userForm,
                                        @RequestParam(name = "isStopped", required = false) Integer isStoppedNumber,
                                        @PathVariable Integer id) throws Exception {

        ModelAndView mav = new ModelAndView();
        userForm.setIsStopped(isStoppedNumber);
        userService.saveUser(userForm);
        return new ModelAndView("redirect:/user-management");
    }
    /*
     * ユーザー編集画面表示
     */
    @GetMapping("/user-edit/{id}")
    public ModelAndView userEdit(@PathVariable Integer id) {
        ModelAndView mav = new ModelAndView();

        UserForm userForm = userService.findById(id);
        userForm.setPassword("");
        mav.addObject("userForm", userForm);

        List<BranchForm> branches = branchService.findAll();
        mav.addObject("branches", branches);

        List<DepartmentForm> departments = departmentService.findAll();
        mav.addObject("departments", departments);

        mav.setViewName("/user-edit");

        return mav;
    }

    /*
     * ユーザー編集処理
     */
    @PutMapping("/edit")
    public ModelAndView userEdit(@Validated @ModelAttribute("userForm")UserForm userForm, BindingResult result) throws Exception {
        ModelAndView mav = new ModelAndView();
        // バリデーション処理
        List<String> errorList = new ArrayList<String>();
        if (result.hasErrors()) {
            for (ObjectError error : result.getAllErrors()) {
                // パスワードが未入力の場合はエラー処理しない
                if (!Objects.equals(error.getDefaultMessage(), "パスワードを入力してください") &&
                    !Objects.equals(error.getDefaultMessage(), "パスワードは半角文字かつ6文字以上20文字以下で入力してください")) {
                    errorList.add(error.getDefaultMessage());
                }
            }
        }
        List<UserForm> users = userService.findByAccount(userForm.getAccount());
        // 既存データでアカウントの重複がないことを前提にListから値を取得
        if (users.size() > 0 && users.get(0).getId() != userForm.getId()) {
            errorList.add("アカウントが重複しています");
        }
        if (!errorList.isEmpty()) {
            List<BranchForm> branches = branchService.findAll();
            mav.addObject("branches", branches);
            List<DepartmentForm> departments = departmentService.findAll();
            mav.addObject("departments", departments);
            mav.addObject("userForm", userForm);
            mav.addObject("validationError", errorList);
            mav.setViewName("/user-registration");
            return mav;
        }
        // パスワード未入力の場合は、そのまま更新する
        mav.addObject("userForm", userForm);
        userService.saveUser(userForm);
        mav.setViewName("redirect:/user-management");
        return mav;
    }
}