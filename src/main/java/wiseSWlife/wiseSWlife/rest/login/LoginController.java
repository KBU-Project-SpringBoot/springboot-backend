package wiseSWlife.wiseSWlife.rest.login;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import wiseSWlife.wiseSWlife.domain.login.LoginService;
import wiseSWlife.wiseSWlife.domain.member.Member;
import wiseSWlife.wiseSWlife.rest.login.form.LoginForm;
import wiseSWlife.wiseSWlife.rest.login.response.LoginResponseData;
import wiseSWlife.wiseSWlife.rest.session.SessionConst;
import wiseSWlife.wiseSWlife.rest.session.form.SessionForm;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor

public class LoginController {

    private final LoginService loginService;


    @RequestMapping("/login")
    @ResponseBody
    public LoginResponseData login(@RequestBody LoginForm form, HttpServletRequest request) {

        Member loggedMember = loginService.login(form.getUsername(), form.getPassword());

        if (loggedMember == null) {
            return new LoginResponseData(HttpStatus.BAD_REQUEST,request.getRequestURI(), "login fail", form.getUsername());
        }

        //success logic

        HttpSession session = request.getSession();
        SessionForm loggedMemSession = new SessionForm(loggedMember.getLoginId());

        // front에서 header에서 뽑아 사용해야함
        session.setAttribute(SessionConst.LOGIN_SESSION_KEY, loggedMemSession);
        return new LoginResponseData(HttpStatus.OK, request.getRequestURI(), "Login Success", loggedMember.getLoginId());
    }
}
