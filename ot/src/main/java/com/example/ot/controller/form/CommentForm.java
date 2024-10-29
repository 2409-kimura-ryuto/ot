package com.example.ot.controller.form;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import java.util.*;

@Getter
@Setter
public class CommentForm {
    private Integer id;

    @NotBlank(message = "メッセージを入力してください")
    @Size(max = 500, message = "500文字以内で入力してください")
    private String text;

    private int userId;
    private int messageId;
    private Date createdDate;
    private Date updatedDate;
}
