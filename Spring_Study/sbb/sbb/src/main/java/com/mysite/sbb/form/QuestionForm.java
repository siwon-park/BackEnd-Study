package com.mysite.sbb.form;

import javax.validation.constraints.*;


public class QuestionForm {
    @NotEmpty(message = "제목은 필수 입력 항목입니다.")
    @Size(max=200)
    private String title;

    @NotEmpty(message = "내용은 필수 입력 항목입니다.")
    private String content;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
