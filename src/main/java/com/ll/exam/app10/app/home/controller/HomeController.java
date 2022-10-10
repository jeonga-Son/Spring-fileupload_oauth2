package com.ll.exam.app10.app.home.controller;

import com.ll.exam.app10.app.member.service.MemberService;
import com.ll.exam.app10.app.security.dto.MemberContext;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;


@Controller
// @RequiredArgsConstructor는 Lombok으로 스프링에서 DI(의존성 주입)의 방법 중에 생성자 주입을 임의의 코드없이 자동으로 설정해주는 어노테이션이다.
@RequiredArgsConstructor
public class HomeController {
    private final MemberService memberService;
    // RequestMapping(요청 매핑)은 요청이 왔을 때 어떤 컨트롤러가 호출이 되어야 하는지 알려주는 지표 같은 것이다.
    @GetMapping("/")
    public String showMain() {
        return "home/main";
    }

    @GetMapping("/about")
    public String showAbout() {
        return "home/about";
    }

    @GetMapping("/currentUserOrigin")
    @ResponseBody
    public Principal currentUserOrigin(Principal principal) {
        return principal;
    }

    @GetMapping("/currentUser")
    @ResponseBody
    public MemberContext currentUser(@AuthenticationPrincipal MemberContext memberContext) {
        return memberContext;
    }
}
