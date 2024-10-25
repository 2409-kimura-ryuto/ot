package com.example.ot.controller;

import com.example.ot.controller.form.UserCommentForm;
import com.example.ot.controller.form.UserMessageForm;
import com.example.ot.service.CommentService;
import com.example.ot.service.MessageService;
import com.example.ot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class OtController {

    @Autowired
    UserService userService;

    @Autowired
    MessageService messageService;

    @Autowired
    CommentService commentService;

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
}
