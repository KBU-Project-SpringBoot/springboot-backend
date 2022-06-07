package wiseSWlife.wiseSWlife.web.controller.community.freeBoard;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import wiseSWlife.wiseSWlife.config.session.SessionConst;
import wiseSWlife.wiseSWlife.config.session.form.SessionForm;
import wiseSWlife.wiseSWlife.domain.community.freeBoard.FreeBoard;
import wiseSWlife.wiseSWlife.domain.repositoryInterface.communityRepository.freeBoardRepository.FreeBoardRepository;
import wiseSWlife.wiseSWlife.web.controller.community.freeBoard.form.FreeBoardPostForm;

import javax.validation.Valid;
import java.util.List;
@Slf4j
@Controller
@RequiredArgsConstructor
public class FreeBoardController {

    private final FreeBoardRepository freeBoardRepository;

    @GetMapping("/community/freeBoard")
    public String freeBoard(@SessionAttribute(name = SessionConst.LOGIN_SESSION_KEY, required = false)SessionForm sessionForm,
                            Model model) {
        List<FreeBoard> allFreeBoard = freeBoardRepository.findAllFreeBoard();
        model.addAttribute("freeBoards", allFreeBoard);
        model.addAttribute("sessionForm", sessionForm);

        return "community/freeBoard/FreeBoard";
    }

    @GetMapping("/community/freeBoard/post")
    public String getFreeBoardPost(@ModelAttribute("freeBoardPostForm")FreeBoardPostForm postForm){
        return "community/freeBoard/FreeBoardPost";
    }

    @PostMapping("/community/freeBoard/post")
    public String getFreeBoardPost(@Valid @ModelAttribute("freeBoardPostForm")FreeBoardPostForm postForm,
                                   BindingResult bindingResult,
                                   @SessionAttribute(name = SessionConst.LOGIN_SESSION_KEY, required = false)SessionForm sessionForm){
        if(bindingResult.hasErrors()){
            return "community/freeBoard/FreeBoardPost";
        }

        // post success logic
        freeBoardRepository.save(new FreeBoard(sessionForm.getSid(), postForm.getFreeBoardPostName(), postForm.getFreeBoardTitle(), postForm.getFreeBoardText()));

        return "redirect:/community/freeBoard";
    }

}
