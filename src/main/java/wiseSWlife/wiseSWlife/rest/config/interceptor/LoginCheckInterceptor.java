package wiseSWlife.wiseSWlife.rest.config.interceptor;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import wiseSWlife.wiseSWlife.rest.session.SessionConst;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Slf4j
public class LoginCheckInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();

        if(session == null || session.getAttribute(SessionConst.LOGIN_SESSION_KEY)==null){
        log.info("미인증 사용자 요청 {}",request);
//        response.sendError(HttpServletResponse.SC_FORBIDDEN);
        response.sendRedirect("/login");

        //403 -> 세션 만료
        response.sendError(HttpServletResponse.SC_FORBIDDEN);
            return false;
        }
        return true;

    }
}
