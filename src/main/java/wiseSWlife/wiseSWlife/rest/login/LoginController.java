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
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor

public class LoginController {

    private final LoginService loginService;


    @RequestMapping("/login")
    @ResponseBody
    public LoginResponseData login(@RequestBody LoginForm form, HttpServletRequest request) throws IOException, InterruptedException {

        Member loggedMember = loginService.login(form.getUsername(), form.getPassword());

        if (loggedMember == null) {
            return new LoginResponseData(HttpStatus.BAD_REQUEST,request.getRequestURI(), "login fail", form.getUsername(),null);
        }

        String command = "C:\\Users\\User\\PycharmProjects\\pythonProjectVenv\\venv\\Scripts\\python.exe"; //명령어
        String arg1 = "C:\\Users\\User\\PycharmProjects\\pythonProjectVenv\\biblebot_scraper\\biblebot-scraper\\test.py";//인지
        ProcessBuilder builder = new ProcessBuilder(command, arg1);
        Process process = builder.start();
        int exitVal = process.waitFor();//자식 프로세스가 종료될 때까지 기다림
        BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream(), "euc-kr")); // 서브 프로세스가 출력하는 내용을 받기 위해
        String line;
        while ((line = br.readLine()) != null) {
            System.out.println(">>>  " + line); // 표준출력에 쓴다
        }
        if(exitVal != 0) {
            System.out.println("서브 프로세스가 비정상 종료되었습니다.");
        }
        //InputStream input = process.getInputStream();//자식 프로세스가 System.out에 출력하는 내용
        //OutputStream output = process.getOutputStream();//자식 프로세스한테 입력값 전달

        //System.out.println(input);
        //success logic

        HttpSession session = request.getSession();
        SessionForm loggedMemSession = new SessionForm(loggedMember.getLoginId());

        // front에서 header에서 뽑아 사용해야함
        session.setAttribute(SessionConst.LOGIN_SESSION_KEY, loggedMemSession);

        return new LoginResponseData(HttpStatus.OK, request.getRequestURI(), "Login Success", loggedMember.getLoginId(),session.getId());
    }
}
