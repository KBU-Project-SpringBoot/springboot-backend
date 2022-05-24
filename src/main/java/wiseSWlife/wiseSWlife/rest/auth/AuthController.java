package wiseSWlife.wiseSWlife.rest.auth;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;
import wiseSWlife.wiseSWlife.rest.auth.response.AuthResponseData;
import wiseSWlife.wiseSWlife.rest.session.SessionConst;
import wiseSWlife.wiseSWlife.rest.session.form.SessionForm;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
@RequiredArgsConstructor
public class AuthController {


    @RequestMapping("/Auth/me")
    @ResponseBody
    public AuthResponseData auth(@SessionAttribute(name = SessionConst.LOGIN_SESSION_KEY) SessionForm form, HttpServletRequest request){
        return new AuthResponseData(HttpStatus.OK,request.getRequestURI()," The current request data has been answered.", form.getLoginId(), form.getSessionId());
    }
}
