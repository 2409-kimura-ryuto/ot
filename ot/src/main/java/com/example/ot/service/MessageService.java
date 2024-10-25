package com.example.ot.service;

import com.example.ot.controller.form.UserMessageForm;
import com.example.ot.repository.MessageRepository;
import com.example.ot.repository.entity.UserMessage;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MessageService {

    @Autowired
    MessageRepository messageRepository;

    /*
     * UserMessageを取得
     */
    public List<UserMessageForm> findAllUserMessage() {
        List<UserMessage> results = messageRepository.findAllUserMessage();
        List<UserMessageForm> messages = setUserMessageForm(results);
        return messages;
    }
    /*
     * DBから取得したUserMessageをFormに変換
     */
    private List<UserMessageForm> setUserMessageForm(List<UserMessage> results) {
        List<UserMessageForm> messages = new ArrayList<>();

        for (UserMessage result : results) {
            UserMessageForm message = new UserMessageForm();
            BeanUtils.copyProperties(result, message);
            messages.add(message);
        }
        return messages;
    }
}
