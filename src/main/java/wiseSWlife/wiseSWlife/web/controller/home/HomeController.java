package wiseSWlife.wiseSWlife.web.controller.home;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import wiseSWlife.wiseSWlife.domain.repositoryInterface.memberRepository.MemberRepository;
import wiseSWlife.wiseSWlife.rest.session.SessionConst;
import wiseSWlife.wiseSWlife.rest.session.form.SessionForm;

@Slf4j
@Controller
@RequiredArgsConstructor
public class HomeController {
    private final MemberRepository memberRepository;

    @GetMapping("/")
    public String home(@SessionAttribute(name = SessionConst.LOGIN_SESSION_KEY, required = false)SessionForm sessionForm, Model model) {

        if(sessionForm == null){
            return "home/home";
        }

        model.addAttribute("sessionForm", sessionForm);
        return "home/loggedHome";

    }

}
