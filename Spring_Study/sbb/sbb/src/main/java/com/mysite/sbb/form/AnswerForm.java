package com.mysite.sbb.form;

import javax.validation.constraints.NotEmpty;

public class AnswerForm {
    @NotEmpty(message = "내용은 필수 입력 항목입니다.")
    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
