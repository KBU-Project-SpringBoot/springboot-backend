package wiseSWlife.wiseSWlife.web.controller.graduation;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import wiseSWlife.wiseSWlife.config.session.SessionConst;
import wiseSWlife.wiseSWlife.config.session.form.SessionForm;
import wiseSWlife.wiseSWlife.domain.graduation.service.ExamScrapping;

import java.io.IOException;
import java.util.Map;

@Slf4j
@Controller
@RequiredArgsConstructor
public class GraduationController {

    private final ExamScrapping examScrapping;

    @GetMapping("/graduation")
    public String Graduation(@SessionAttribute(name = SessionConst.LOGIN_SESSION_KEY,required = false)SessionForm sessionForm,
                             Model model) throws IOException, InterruptedException {

        if(sessionForm.getIntCookie().isEmpty()){
            return "redirect:/login";
        }

        String intCookie = sessionForm.getIntCookie();
        Map<String, Boolean> examMap = examScrapping.scrapping(intCookie);

        model.addAttribute("examMap", examMap);
        model.addAttribute("sessionForm", sessionForm);
        return "graduate/graduation";
    }

}
