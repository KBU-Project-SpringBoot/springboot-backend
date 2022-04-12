package wiseSWlife.wiseSWlife.rest.login;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.web.bind.annotation.*;
import wiseSWlife.wiseSWlife.domain.login.LoginService;
import wiseSWlife.wiseSWlife.domain.member.Member;
import wiseSWlife.wiseSWlife.rest.login.form.LoginForm;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor

public class LoginController {

    private final LoginService loginService;

    @PostMapping("/login")
    @ResponseBody
    public Map login(@RequestBody LoginForm form){
        Map<String, String> result = new HashMap<>();
        Member loggedMember = loginService.login(form.getUsername(), form.getPassword());
        if(loggedMember == null){
            result.put("message","로그인 실패");
            result.put("status","400");
        }else{

            //success logic




            result.put("message","로그인 성공");
            result.put("status","200");
        }
        return result;
    }

}
