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
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
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
            LocalDateTime now = LocalDateTime.now();
            now = now.plusMinutes(30);
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            end = now.format(dateTimeFormatter);
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

        SimpleDateFormat sdFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        for (UserMessage result : results) {
            UserMessageForm message = new UserMessageForm();
            BeanUtils.copyProperties(result, message);
            // 投稿日時の表記設定
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(message.getCreatedDate());
            String postDate = String.valueOf(calendar.get(Calendar.YEAR)) + "年" +
                              String.valueOf(calendar.get(Calendar.MONTH) + 1) + "月" +
                              String.valueOf(calendar.get(Calendar.DAY_OF_MONTH)) + "日　" +
                              String.valueOf(calendar.get(Calendar.HOUR_OF_DAY)) + "時" +
                              String.valueOf(calendar.get(Calendar.MINUTE)) + "分" +
                              String.valueOf(calendar.get(Calendar.SECOND)) + "秒";
            message.setPostDate(postDate);
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
