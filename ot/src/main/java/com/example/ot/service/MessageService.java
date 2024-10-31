package com.example.ot.service;

import com.example.ot.controller.form.MessageForm;
import com.example.ot.controller.form.UserMessageForm;
import com.example.ot.repository.MessageRepository;
import com.example.ot.repository.entity.Message;
import com.example.ot.repository.UserMessageRepository;
import com.example.ot.repository.entity.UserMessage;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class MessageService {

    @Autowired
    UserMessageRepository userMessageRepository;
    @Autowired
    MessageRepository messageRepository;

    /*
     * UserMessageを取得
     */
    public List<UserMessageForm> findAllUserMessage(String start, String end, String category) {

        if (!start.isBlank()) {
            start += " 00:00:00";
        } else {
            start = "2022-01-01 00:00:00";
        }
        SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if (!end.isBlank()) {
            end += " 23:59:59";
        } else {
            Date endDate = new Date();
            end = sdFormat.format(endDate);

        }
        List<UserMessageForm> messages = null;
        try{
            Date startDate = sdFormat.parse(start);
            Date endDate = sdFormat.parse(end);
            if (category.isBlank()) {
                List<UserMessage> results = userMessageRepository.findAllUserMessage(startDate, endDate);
                messages = setUserMessageForm(results);

            } else {
                List<UserMessage> results = userMessageRepository.findAllUserMessage(startDate, endDate, "%" + category + "%");
                messages = setUserMessageForm(results);
            }
            return messages;
        } catch (ParseException e) {
            // 日付での絞り込みが失敗した場合、nullを返す
            return messages;
        }
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

    /*
     * レコード追加
     */
    public void saveMessage(MessageForm reqMessage) {
        Message saveMessage = setMessagesEntity(reqMessage);
        messageRepository.save(saveMessage);
    }

    /*
     *リストから取得した情報をentityに設定
     */
    private Message setMessagesEntity(MessageForm reqMessage) {
        Message message = new Message();
        BeanUtils.copyProperties(reqMessage, message);
        return message;
    }

    /*
     * レコード削除
     */
    public void deleteMessage(Integer id) {
        messageRepository.deleteById(id);
    }
}
