package com.mysite.sbb.controller;

import com.mysite.sbb.model.Question;
// import com.mysite.sbb.repository.QuestionRepository; // 컨트롤러에서 더 이상레포지토리로 직접 접근하지 않음
import com.mysite.sbb.service.QuestionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.*;

@RequestMapping("/question") // URL Prefix => 컨트롤러의 클래스 단위 url 맵핑
@Controller
public class QuestionController {

    private final QuestionService questionService; // 컨트롤러에서 서비스를 통해 레포지토리로 접근

    public QuestionController(QuestionService questionService) {
        this.questionService = questionService;
    }

    @RequestMapping("/list") // URL Prefix에 의해 "/question/list"으로 맵핑
    public String list(Model model) {
        List<Question> questionList = this.questionService.getList();
        model.addAttribute("questionList", questionList); // 모델 객체에 "questionList"라는 이름으로 questionList 객체를 저장
        return "question_list";
    }

    // QuestionController
    @RequestMapping(value = "/detail/{id}") // URL Prefix에 의해 "/question/detail/{id}"로 맵핑
    public String detail(Model model, @PathVariable("id") Integer id) {
        Question question = this.questionService.getQuestion(id);
        model.addAttribute("question", question); // 모델 객체에 "question"이라는 이름으로 question 객체를 저장
        return "question_detail";
    }

}
