package wiseSWlife.wiseSWlife.web.controller.community.freeBoard;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import wiseSWlife.wiseSWlife.config.session.SessionConst;
import wiseSWlife.wiseSWlife.config.session.form.SessionForm;
import wiseSWlife.wiseSWlife.domain.community.freeBoard.FreeBoard;
import wiseSWlife.wiseSWlife.domain.community.freeBoard.FreeBoardComment;
import wiseSWlife.wiseSWlife.domain.repositoryInterface.communityRepository.freeBoardRepository.FreeBoardCommentRepository;
import wiseSWlife.wiseSWlife.domain.repositoryInterface.communityRepository.freeBoardRepository.FreeBoardRepository;
import wiseSWlife.wiseSWlife.web.controller.community.freeBoard.form.FreeBoardCommentForm;
import wiseSWlife.wiseSWlife.web.controller.community.freeBoard.form.FreeBoardPostForm;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Slf4j
@Controller
@RequiredArgsConstructor
public class FreeBoardController {

    private final FreeBoardRepository freeBoardRepository;
    private final FreeBoardCommentRepository freeBoardCommentRepository;

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
    public String postFreeBoardPost(@Valid @ModelAttribute("freeBoardPostForm")FreeBoardPostForm postForm,
                                   BindingResult bindingResult,
                                   @SessionAttribute(name = SessionConst.LOGIN_SESSION_KEY, required = false)SessionForm sessionForm){
        if(bindingResult.hasErrors()){
            return "community/freeBoard/FreeBoardPost";
        }

        // post success logic
        freeBoardRepository.save(new FreeBoard(sessionForm.getSid(), postForm.getFreeBoardPostName(), postForm.getFreeBoardTitle(), postForm.getFreeBoardText()));

        return "redirect:/community/freeBoard";
    }

    @GetMapping("/community/freeBoard/{freeBoardSeq}")
    public String requestFreeBoard(@ModelAttribute("freeBoardCommentForm")FreeBoardCommentForm commentForm,
                               @PathVariable("freeBoardSeq")Long freeBoardSeq,
                               Model model) {

        Optional<FreeBoard> findFreeBoard = freeBoardRepository.findFreeBoardByFreeBoardSeq(freeBoardSeq);
        if(findFreeBoard.isEmpty()){
            log.error("access to nonexistent FreeBoardSeq = {}",freeBoardSeq);
            return "redirect:/community/freeBoard";
        }

        // success logic
        model.addAttribute("freeBoard", findFreeBoard.get());
        model.addAttribute("commentList", freeBoardCommentRepository.findAllFreeBoardCommentByFreeBoardSeq(freeBoardSeq));
        return "community/freeBoard/RequestFreeBoard";
    }

    @PostMapping("/community/freeBoard/{freeBoardSeq}")
    public String commentPost(@Valid @ModelAttribute("freeBoardCommentForm")FreeBoardCommentForm commentForm,
                              BindingResult bindingResult,
                              Model model,
                              @PathVariable("freeBoardSeq")Long freeBoardSeq,
                              @SessionAttribute(name = SessionConst.LOGIN_SESSION_KEY, required = false)SessionForm sessionForm
                              ){

        if(bindingResult.hasErrors()){
            Optional<FreeBoard> findFreeBoard = freeBoardRepository.findFreeBoardByFreeBoardSeq(freeBoardSeq);
            if(findFreeBoard.isEmpty()){
                log.error("access to nonexistent FreeBoardSeq = {}",freeBoardSeq);
                return "redirect:/community/freeBoard";
            }
            model.addAttribute("freeBoard", findFreeBoard.get());
            model.addAttribute("commentList", freeBoardCommentRepository.findAllFreeBoardCommentByFreeBoardSeq(freeBoardSeq));
            return "community/freeBoard/RequestFreeBoard";
        }


        freeBoardCommentRepository.save(new FreeBoardComment(sessionForm.getSid(),freeBoardSeq, commentForm.getFreeBoardCommentNickName(), new Date(), commentForm.getFreeBoardComment()));
        return "redirect:/community/freeBoard/"+freeBoardSeq;
    }

}
