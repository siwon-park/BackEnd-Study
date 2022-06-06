package com.mysite.sbb;

import com.mysite.sbb.model.Answer;
import com.mysite.sbb.model.Question;
import com.mysite.sbb.repository.AnswerRepository;
import com.mysite.sbb.repository.QuestionRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
class SbbApplicationTests {

	@Autowired // 스프링부트의 DI를 적용시키는 애너테이션 => 해당 객체를 스프링부트가 자동 생성함
	private QuestionRepository questionRepository;

	@Autowired
	private AnswerRepository answerRepository;

	@Test // 해당 메서드가 테스트 메서드임을 나타내는 애너테이션
	void testCreateQuestion() {
		Question q1 = new Question();
		q1.setTitle("SBB가 무엇인가요??");
		q1.setContent("SBB에 대해 알고싶습니다.");
		q1.setCreatedAt(LocalDateTime.now());
		this.questionRepository.save(q1); // q1을 저장함

		Question q2 = new Question();
		q2.setTitle("스프링부트 모델 질문이있습니다");
		q2.setContent("id는 자동으로 생성되나요?");
		q2.setCreatedAt(LocalDateTime.now());
		this.questionRepository.save(q2);
	}

	@Test
	void testfindAll() {
		List<Question> all = this.questionRepository.findAll();
		assertEquals(2, all.size());

		Question q1 = all.get(0);
		assertEquals("SBB가 무엇인가요??", q1.getTitle());
	}

	@Test
	void testfindById() {
		Optional<Question> oq = this.questionRepository.findById(1);
		if (oq.isPresent()) {
			Question q1 = oq.get();
			assertEquals("SBB가 무엇인가요??", q1.getTitle());
		}
	}

	@Test
	void testfindByTitle() {
		Question q = this.questionRepository.findByTitle("SBB가 무엇인가요??");
		assertEquals(1, q.getId());
	}

	@Test
	void testfindByTitleAndContent() {
		Question q = this.questionRepository.findByTitleAndContent(
				"SBB가 무엇인가요??", "SBB에 대해 알고싶습니다."
		);
		assertEquals(1, q.getId());
	}

	@Test
	void testfindByTitleLike() {
		List<Question> qList = this.questionRepository.findByTitleLike("SBB%");
		Question q = qList.get(0);
		assertEquals("SBB가 무엇인가요??", q.getTitle());
	}

	@Test
	void testUpdateQuestion() {
		Optional<Question> oq = this.questionRepository.findById(1);
		assertTrue(oq.isPresent());
		Question q = oq.get();
		q.setTitle("수정된 제목");
		this.questionRepository.save(q);
	}

	@Test
	void testDeleteQuestion() {
		assertEquals(2, this.questionRepository.count());
		Optional<Question> oq = this.questionRepository.findById(1);
		assertTrue(oq.isPresent());
		Question q = oq.get();
		this.questionRepository.delete(q);
		assertEquals(1, this.questionRepository.count());
	}

	@Test
	void testCreateAnswer() {
		Optional<Question> oq = this.questionRepository.findById(2);
		assertTrue(oq.isPresent());
		Question q = oq.get();
		Answer a = new Answer();
		a.setContent("네 자동으로 생성됩니다.");
		a.setQuestion(q); // 어떤 질문에 대한 답변인지 Question 객체 q를 지정해줌
		a.setCreatedAt(LocalDateTime.now());
		this.answerRepository.save(a);
	}

	@Test
	void testfindAnswer() {
		Optional<Answer> oa = this.answerRepository.findById(1);
		assertTrue(oa.isPresent());
		Answer a = oa.get();
		assertEquals(2, a.getQuestion().getId()); // 답변이 달린 질문의 id는 2이므로 true
	}

	@Transactional // 메서드가 종료될 때까지 DB세션을 유지시켜줌
	@Test
	void testfindAnswerByQuestion() {
		// 질문에 달린 답변 찾기(역참조)
		Optional<Question> oq = this.questionRepository.findById(2);
		assertTrue(oq.isPresent());
		Question q = oq.get();

		List<Answer> answerList = q.getAnswerList();
		assertEquals(1, answerList.size());
		assertEquals("네 자동으로 생성됩니다.", answerList.get(0).getContent());
	}
}
