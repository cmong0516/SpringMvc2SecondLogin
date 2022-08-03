package hello.login.web.login;

import hello.login.domain.member.Member;
import hello.login.web.SessionConst;
import hello.login.web.session.SessionManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Slf4j
@Controller
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;
    private final SessionManager sessionManager;

    @GetMapping("/login")
    public String loginForm(@ModelAttribute LoginForm loginForm) {
        return "login/loginForm";
    }

    //    @PostMapping("/login")
//    public String login(@Valid @ModelAttribute LoginForm form, BindingResult result) {
//        if (result.hasErrors()) {
//            return "login/loginForm";
//        }
//
//        Member loginMember = loginService.login(form.getLoginId(), form.getPassword());
//        log.info("login? {}", loginMember);
//
//        if (loginMember == null) {
//            result.reject("loginFail", "아이디 또는 비밀번호가 맞지 않습니다.");
//            return "login/loginForm";
//        }
//
//        return "redirect:/";
//    }
//    @PostMapping("/login")
//    public String login(@Valid @ModelAttribute LoginForm form, BindingResult bindingResult, HttpServletResponse response) {
//        if (bindingResult.hasErrors()) {
//            return "login/loginForm";
//        }
//
//        Member loginMember = loginService.login(form.getLoginId(), form.getPassword());
//
//        if (loginMember == null) {
//            bindingResult.reject("loginFail", "아이디 또는 비밀번호가 맞지 않습니다.");
//            return "login/loginForm";
//        }
//// 쿠키로 로그인 처리를 할경우 보안에 취약하다.
////        Cookie idCookie = new Cookie("memberId", String.valueOf(loginMember.getId()));
////        response.addCookie(idCookie);
//        sessionManager.createSession(loginMember, response);
//
//
//        return "redirect:/";
//    }
//
//    @PostMapping("/logout")
//    public String logout(HttpServletRequest request) {
////        expireCookie(response, "memberId");
//        sessionManager.expire(request);
//        return "redirect:/";
//    }
    // 직접 만든 Session 말고 서블릿에는 이미 Session 의 기능이 구현된 HttpSession 이 있다.
    // 쿠키 이름은 JSESSIONID , 값은 추정 불가능한 랜덤 값이다.

    private void expireCookie(HttpServletResponse response, String cookieName) {
        Cookie cookie = new Cookie(cookieName, null);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
    }

    @PostMapping("/login")
    public String login(@Valid @ModelAttribute LoginForm form, BindingResult bindingResult, HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            return "login/loginForm";
        }

        Member loginMember = loginService.login(form.getLoginId(), form.getPassword());

        if (loginMember == null) {
            bindingResult.reject("loginFail", "아이디 또는 비밀번호가 맞지 않습니다.");
            return "login/loginForm";
        }

        // error 가 있거나 id or password 가 틀리면 loginForm 으로
        // 아래는 성공로직.

        HttpSession session = request.getSession();
        session.setAttribute(SessionConst.LOGIN_MEMBER, loginMember);
        return "redirect:/";
    }

    @PostMapping("/logout")
    public String logoutV3(HttpServletRequest request) {
        // session 을 조회해서 없으면 null 을 반환.
        HttpSession session = request.getSession(false);
        // session 이 null 이 아니면 세션 제거.
        if (session != null) {
            session.invalidate();
        }
        return "redirect:/";
    }
}
