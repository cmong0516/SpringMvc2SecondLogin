package hello.login.web;

import hello.login.domain.member.Member;
import hello.login.domain.member.MemberRepository;
import hello.login.domain.member.MemberRepositoryIF;
import hello.login.web.argumentresolver.Login;
import hello.login.web.session.SessionManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Slf4j
@Controller
@RequiredArgsConstructor
public class HomeController {

    private final MemberRepository memberRepository;
    private final MemberRepositoryIF memberRepositoryIF;
    private final SessionManager sessionManager;

//    @GetMapping("/")
//    public String home() {
//        return "home";
//    }

    //    @GetMapping("/")
//    public String homeLogin(@CookieValue(name = "memberId",required = false) Long memberId, Model model) {
//        if (memberId == null) {
//            return "home";
//        }
//
//        Optional<Member> findById = memberRepositoryIF.findById(memberId);
//        Member loginMember = findById.get();
//
//        if (loginMember == null) {
//            return "home";
//        }
//
//        model.addAttribute("member", loginMember);
//        return "loginHome";
//    }
// 직접 만든 세션 사용.
//    @GetMapping("/")
//    public String homeLoginV2(HttpServletRequest request,Model model) {
//        Member member = (Member) sessionManager.getSession(request);
//        if (member == null) {
//            return "home";
//        }
//        model.addAttribute("member", member);
//        return "loginHome";
//    }

//    @GetMapping("/")
//    public String homeLoginV3(HttpServletRequest request, Model model) {
//        HttpSession session = request.getSession(false);
//        if (session == null) {
//            return "home";
//        }
//
//        Member loginMember = (Member) session.getAttribute(SessionConst.LOGIN_MEMBER);
//
//        if (loginMember == null) {
//            return "home";
//        }
//
//        model.addAttribute("member", loginMember);
//        return "loginHome";
//    }

//    @GetMapping("/")
//    public String homeLoginV3(@SessionAttribute(name = "loginMember",required = false) Member loginMember, Model model) {
////        HttpSession session = request.getSession(false);
////        if (session == null) {
////            return "home";
////        }
//
////        Member loginMember = (Member) session.getAttribute(SessionConst.LOGIN_MEMBER);
//
//        if (loginMember == null) {
//            return "home";
//        }
//
//        model.addAttribute("member", loginMember);
//        return "loginHome";
//    }
    // Login 여부를 애노테이션 으로 만들어서 작성할 것이다.

    @GetMapping("/")
    public String homeLoginArgumentResolver(@Login Member loginMember, Model model) {
        if (loginMember == null) {
            return "home";
        }

        model.addAttribute("member", loginMember);
        return "loginHome";
    }
}