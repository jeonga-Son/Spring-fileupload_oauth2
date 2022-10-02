package com.ll.exam.app10.app.member.controller;

import com.ll.exam.app10.app.member.entity.Member;
import com.ll.exam.app10.app.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {
    private final MemberService memberService;

    @GetMapping("/join")
    public String showJoin() {
        return "member/join";
    }

    @PostMapping("/join")
    public String join(String username, String password, String email, MultipartFile profileImg, HttpSession session) {
        Member oldMember = memberService.getMemberByUsername(username);

        if ( oldMember != null ) {
            return "redirect:/?errorMsg=Already done.";
        }

        Member member = memberService.join(username,"{noop}" + password, email, profileImg);

        session.setAttribute("loginedMemberId", member.getId());

        return "redirect:/member/profile";
    }

    @GetMapping("/profile")
    public String showProfile(HttpSession session) {

        // getParameter()와 getAttribute() 메소드는 HttpServletRequest 클래스 내에 있다.
        // attribute란 Servlet간 공유하는 객체이다. getAttribute()는 반환유형이 Object이다.
        // 이전에 다른 JSP 또는 Servlet 페이지에 설정된 매개변수를 가져오는데 사용한다.
        // 따라서 이전의 setAttribute()  속성을 통한 설정이 있어야 한다. 그렇지 않으면 null값을 가져온다.
        // Controller Servlet 등에서 View로 전달할 때 사용한다.


        // Long은 null을 사용할수 있고, long은 null을 사용할수 없다.
        //그 외에도 메모리 할당 크기의 차이가 있다.
        Long loginedMemberId = (Long) session.getAttribute("loginedMemberId");
        boolean isLogined = loginedMemberId != null;

        if ( isLogined == false ) {
            return "redirect:/?errorMsg=Need to login!";
        }

        return "member/profile";
    }
}