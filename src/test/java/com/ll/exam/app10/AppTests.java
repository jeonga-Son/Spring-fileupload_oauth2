package com.ll.exam.app10;

import com.ll.exam.app10.app.home.controller.HomeController;
import com.ll.exam.app10.app.member.service.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import javax.transaction.Transactional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
// private MockMvc mvc; => 컨트롤러 테스트. 이것을 하기 위해서는 @AutoConfigureMockMvc이 필요하다. 세트이다.
@AutoConfigureMockMvc
// @Transactional은 여기서 실행된 것은 실제 DB에 반영이 안되도록 하는 것 이다.
@Transactional
//  현재의 프로파일(환경)을 지정하도록 도와주는 어노테이션이 @ActiveProfiles 이다.
@ActiveProfiles({"base-addi", "test"})
class AppTests {

	@Autowired
	private MockMvc mvc;
	@Autowired
	private MemberService memberService;

	@Test
	@DisplayName("메인화면에서는 안녕이 나와야 한다.")
	void t1() throws Exception {
		// WHEN
		// GET

		ResultActions resultActions = mvc // mvc는 테스트하기 위해 필요한 기능이 다 들어있는 일종의 브라우저라고 생각하면 된다.
				.perform(get("/"))
				.andDo(print());// 요청이 날라오면 화면에 출력을 해라.

		// THEN
		// 안녕
		resultActions
				.andExpect(status().is2xxSuccessful())
				.andExpect(handler().handlerType(HomeController.class)) // .perform(get("/"))를 수행하면 HomeController가 수형되는 것 검증.
				.andExpect(handler().methodName("main"))
				.andExpect(content().string(containsString("안녕"))); // import할 때 client가 아닌 servlet으로 해야한다.
	}

	@Test
	@DisplayName("회원의 수")
	@Rollback(false)
	void t2() {
		long count = memberService.count();
		assertThat(count).isGreaterThan(0);
	}
}
