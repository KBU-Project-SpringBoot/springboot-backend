package wiseSWlife.wiseSWlife.web.controller.login;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import wiseSWlife.wiseSWlife.global.session.SessionConst;
import wiseSWlife.wiseSWlife.global.session.form.SessionForm;
import wiseSWlife.wiseSWlife.dto.member.Member;
import wiseSWlife.wiseSWlife.dto.loginForm.LoginForm;
import wiseSWlife.wiseSWlife.service.login.LoginService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.io.IOException;
import java.sql.SQLException;


@Slf4j
@Controller
@RequiredArgsConstructor
public class LoginController {
    private final LoginService loginService;

    @GetMapping("/login")
    public String loginForm(@ModelAttribute("loginForm") LoginForm form) {
        return "login/loginForm";
    }

    @PostMapping("/login")
    public String login(@Valid @ModelAttribute("loginForm") LoginForm form, BindingResult bindingResult,
                        HttpServletRequest request,
                        @RequestParam(defaultValue = "/") String redirectURL) throws IOException, InterruptedException, SQLException {
        if(bindingResult.hasErrors()){
            return "login/loginForm";
        }
        Member loginMember = loginService.login(form.getLoginId(), form.getPassword());

        if (loginMember == null) {
            log.warn("Login Fail \n info [id = {}] [pw = {}] ",form.getLoginId(), form.getPassword());
            bindingResult.reject("loginFail", "You entered the wrong id pw");
            log.warn("Login Fail bindingResult = {}", bindingResult);
            return "login/loginForm";
        }

        HttpSession session = request.getSession();
        SessionForm sessionForm = new SessionForm(loginMember.getSid(),loginMember.getName(), loginMember.getMajor(), loginMember.getIntCookie());
        session.setAttribute(SessionConst.LOGIN_SESSION_KEY, sessionForm);
        log.info("Login Success [redirectURL = {} ]", redirectURL);
        return "redirect:"+redirectURL;
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        return "redirect:/";
    }
}
