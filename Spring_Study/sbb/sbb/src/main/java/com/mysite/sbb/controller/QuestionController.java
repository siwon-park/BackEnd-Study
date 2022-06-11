package com.mysite.sbb.controller;

import com.mysite.sbb.form.AnswerForm;
import com.mysite.sbb.form.QuestionForm;
import com.mysite.sbb.model.Question;
// import com.mysite.sbb.repository.QuestionRepository; // 컨트롤러에서 더 이상레포지토리로 직접 접근하지 않음
import com.mysite.sbb.service.QuestionService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
    public String detail(Model model, @PathVariable("id") Integer id, AnswerForm answerForm) {
        Question question = this.questionService.getQuestion(id);
        model.addAttribute("question", question); // 모델 객체에 "question"이라는 이름으로 question 객체를 저장
        return "question_detail";
    }

    // 질문 등록 form html get 요청
    @GetMapping("/create")
    public String createQuestion(QuestionForm questionForm) {
        // 매개변수로 바인당한 객체는 Model 객체로 전달하지 않아도 템플릿에서 사용 가능함
        return "question_create_form";
    }
    
    // 질문 등록 요청 처리(post)
    @PostMapping("/create")
    public String createQuestion(@Valid QuestionForm questionForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) { // form validation을 통과하지 못하면
            return "question_create_form"; // 질문 생성 form으로 이동(현위치)
        }
        this.questionService.create(questionForm.getTitle(), questionForm.getContent()); // QuestionService의 create메서드를 통해 질문 생성
        return "redirect:/question/list"; // 질문 등록 후 질문 목록(메인)으로 리다이렉트
    }
}
