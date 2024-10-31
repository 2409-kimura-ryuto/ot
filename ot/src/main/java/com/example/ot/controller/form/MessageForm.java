package com.example.ot.controller.form;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import java.util.*;

@Getter
@Setter
public class MessageForm {
    private int id;

    @Size(max = 30, message = "件名は30文字以内で入力してください")
    private String title;

    @Size(max = 1000, message = "本文は1000文字以内で入力してください")
    private String text;

    @Size(max = 10, message = "カテゴリは10文字以内で入力してください")
    private String category;
    private int userId;
    private Date createdDate;
    private Date updatedDate;

    public @AssertFalse(message = "件名を入力してください") boolean isTitleBlank() {
        return title.isBlank();
    }
    public @AssertFalse(message = "本文を入力してください") boolean isTextBlank() {
        return text.isBlank();
    }
    public @AssertFalse(message = "カテゴリを入力してください") boolean isCategoryBlank() {
        return category.isBlank();
    }
}
