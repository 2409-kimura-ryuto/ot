package com.example.ot.controller.form;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class UserForm {
    private int id;

    @NotBlank(message = "アカウントを入力してください")
    @Pattern(regexp = "^[a-zA-Z0-9]{6,20}+$", message = "アカウントは半角英数字かつ6文字以上20文字以下で入力してください")
    private String account;

    @NotBlank(message = "パスワードを入力してください")
    @Pattern(regexp = "^[a-zA-Z]{6,20}+$", message = "パスワードは半角文字かつ6文字以上20文字以下で入力してください")
    private String password;

    @NotBlank(message = "氏名を入力してください")
    @Size(max = 10, message = "氏名は10文字以下で入力してください")
    private String name;
    private int branchId;
    private int departmentId;
    private int isStopped;
    private Date createdDate;
    private Date updatedDate;

    // ユーザー登録用に追記
    private String passwordRetype;

    public @AssertTrue(message = "パスワードと確認用パスワードが一致しません") boolean isEquals() {
        return password.equals(passwordRetype);
    }

    /*
     *
     */
    public @AssertTrue(message = "支社と部署の組み合わせが不正です") boolean combination() {

        if (branchId == 1) {
            // 本社
            return switch (departmentId) {
                // 総務人事部
                case 1 -> true;
                // 情報管理部
                case 2 -> true;
                default -> false;
            };
        } else {
            // 支社
            return switch (departmentId) {
                // 営業部
                case 3 -> true;
                // 技術部
                case 4 -> true;
                default -> false;
            };
        }
    }
}
