package wiseSWlife.wiseSWlife.web.controller.community.freeBoard;

import com.sun.tools.jconsole.JConsoleContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import wiseSWlife.wiseSWlife.global.session.SessionConst;
import wiseSWlife.wiseSWlife.global.session.form.SessionForm;
import wiseSWlife.wiseSWlife.model.community.freeBoard.FreeBoard;
import wiseSWlife.wiseSWlife.model.community.freeBoard.FreeBoardComment;
import wiseSWlife.wiseSWlife.db.repository.communityRepository.freeBoardRepository.FreeBoardCommentRepository;
import wiseSWlife.wiseSWlife.db.repository.communityRepository.freeBoardRepository.FreeBoardRepository;
import wiseSWlife.wiseSWlife.model.community.freeBoard.form.FreeBoardCommentForm;
import wiseSWlife.wiseSWlife.model.community.freeBoard.form.FreeBoardPostForm;

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
    public String freeBoard(@SessionAttribute(name = SessionConst.LOGIN_SESSION_KEY, required = false) SessionForm sessionForm,
                            Model model) {
        List<FreeBoard> allFreeBoard = freeBoardRepository.findAllFreeBoard();
        System.out.println(allFreeBoard);
        model.addAttribute("freeBoards", allFreeBoard);
        model.addAttribute("sessionForm", sessionForm);

        return "community/freeBoard/FreeBoard";
    }

    @GetMapping("/community/freeBoard/post")
    public String getFreeBoardPost(@ModelAttribute("freeBoardPostForm") FreeBoardPostForm postForm) {
        return "community/freeBoard/FreeBoardPost";
    }

    @PostMapping("/community/freeBoard/post")
    public String postFreeBoardPost(@Valid @ModelAttribute("freeBoardPostForm") FreeBoardPostForm postForm,
                                    BindingResult bindingResult,
                                    @SessionAttribute(name = SessionConst.LOGIN_SESSION_KEY, required = false) SessionForm sessionForm) {
        if (bindingResult.hasErrors()) {
            return "community/freeBoard/FreeBoardPost";
        }

        // post success logic
        freeBoardRepository.save(new FreeBoard(sessionForm.getSid(), postForm.getFreeBoardPostName(), postForm.getFreeBoardTitle(), postForm.getFreeBoardText()));

        return "redirect:/community/freeBoard";
    }

    // freeBoard Get request Controller
    @GetMapping("/community/freeBoard/{freeBoardSeq}")
    public String requestFreeBoard(@ModelAttribute("freeBoardCommentForm") FreeBoardCommentForm commentForm,
                                   @PathVariable("freeBoardSeq") Long freeBoardSeq,
                                   @SessionAttribute(name = SessionConst.LOGIN_SESSION_KEY, required = false) SessionForm sessionForm,
                                   Model model) {

        Optional<FreeBoard> findFreeBoard = freeBoardRepository.findFreeBoardByFreeBoardSeq(freeBoardSeq);
        if (findFreeBoard.isEmpty()) {
            log.error("access to nonexistent FreeBoardSeq = {}", freeBoardSeq);
            return "redirect:/community/freeBoard";
        }

        // success logic
        model.addAttribute("sessionSid", sessionForm.getSid());
        model.addAttribute("freeBoard", findFreeBoard.get());
        model.addAttribute("commentList", freeBoardCommentRepository.findAllFreeBoardCommentByFreeBoardSeq(freeBoardSeq));
        return "community/freeBoard/RequestFreeBoard";
    }


    @GetMapping("/community/freeBoard/update/{freeBoardSeq}")
    public String requestFreeBoardUpdate(@SessionAttribute(name = SessionConst.LOGIN_SESSION_KEY) SessionForm form,
                                         @PathVariable Long freeBoardSeq,
                                         Model model) {

        Optional<FreeBoard> findFreeBoard = freeBoardRepository.findFreeBoardByFreeBoardSeq(freeBoardSeq);
        if (findFreeBoard.isEmpty()) {
            return "redirect:/community/freeBoard";
        }
        FreeBoard pastFreeBoard = findFreeBoard.get();
        //success logic
        model.addAttribute("freeBoardPostForm", new FreeBoardPostForm(pastFreeBoard.getFreeBoardNickName(),
                pastFreeBoard.getFreeBoardTitle(), pastFreeBoard.getFreeBoardText()));

        return "/community/freeBoard/FreeBoardUpdate";
    }

    @PostMapping("/community/freeBoard/update/{freeBoardSeq}")
    public String freeBoardUpdate(@SessionAttribute(name = SessionConst.LOGIN_SESSION_KEY) SessionForm sessionForm,
                                  @PathVariable Long freeBoardSeq,
                                  @Valid @ModelAttribute("freeBoardPostForm") FreeBoardPostForm updatedFreeBoard, BindingResult bindingResult) {
        Optional<FreeBoard> findFreeBoard = freeBoardRepository.findFreeBoardByFreeBoardSeq(freeBoardSeq);
        if (findFreeBoard.isEmpty()) {
            return "redirect:/community/freeBoard";
        }

        if (bindingResult.hasErrors()) {
            return "community/freeBoard/FreeBoardUpdate";
        }

        FreeBoard freeBoard = findFreeBoard.get();
        if (sessionForm.getSid().equals(freeBoard.getFreeBoardSid())) {
            freeBoard.setFreeBoardTitle(updatedFreeBoard.getFreeBoardTitle());
            freeBoard.setFreeBoardNickName(updatedFreeBoard.getFreeBoardPostName());
            freeBoard.setFreeBoardText(updatedFreeBoard.getFreeBoardText());
            freeBoard.setDate(new Date());
            freeBoardRepository.updateFreeBoard(freeBoardSeq, freeBoard);
        }
        return "redirect:/community/freeBoard/" + freeBoardSeq;
    }

    @GetMapping("/community/freeBoard/remove/{freeBoardSeq}")
    public String freeBoardRemove(@PathVariable("freeBoardSeq") Long freeBoardSeq,
                                  @SessionAttribute(name = SessionConst.LOGIN_SESSION_KEY, required = false) SessionForm sessionForm) {
        Optional<FreeBoard> findFreeBoard = freeBoardRepository.findFreeBoardByFreeBoardSeq(freeBoardSeq);

        if (findFreeBoard.isEmpty()) {
            return "redirect:/community/freeBoard";
        }

        if (findFreeBoard.get().getFreeBoardSid().equals(sessionForm.getSid())) {
            freeBoardRepository.removeFreeBoardByFreeBoardSeq(freeBoardSeq);
        }
        return "redirect:/community/freeBoard";
    }


    //FreeBoardComment Controller

    @PostMapping("/community/freeBoard/{freeBoardSeq}")
    public String commentPost(@Valid @ModelAttribute("freeBoardCommentForm") FreeBoardCommentForm commentForm,
                              BindingResult bindingResult,
                              Model model,
                              @PathVariable("freeBoardSeq") Long freeBoardSeq,
                              @SessionAttribute(name = SessionConst.LOGIN_SESSION_KEY, required = false) SessionForm sessionForm
    ) {

        if (bindingResult.hasErrors()) {
            Optional<FreeBoard> findFreeBoard = freeBoardRepository.findFreeBoardByFreeBoardSeq(freeBoardSeq);
            if (findFreeBoard.isEmpty()) {
                log.error("access to nonexistent FreeBoardSeq = {}", freeBoardSeq);
                return "redirect:/community/freeBoard";
            }

            model.addAttribute("freeBoard", findFreeBoard.get());
            model.addAttribute("commentList", freeBoardCommentRepository.findAllFreeBoardCommentByFreeBoardSeq(freeBoardSeq));
            model.addAttribute("sessionSid", sessionForm.getSid());
            return "community/freeBoard/RequestFreeBoard";
        }


        freeBoardCommentRepository.save(new FreeBoardComment(sessionForm.getSid(), freeBoardSeq, commentForm.getFreeBoardCommentNickName(), commentForm.getFreeBoardComment()));
        return "redirect:/community/freeBoard/" + freeBoardSeq;
    }

    @GetMapping("/community/freeBoard/removeComment/{freeBoardSeq}/{freeBoardCommentSeq}")
    public String commentRemove(@PathVariable("freeBoardSeq") Long freeBoardSeq,
                                @PathVariable("freeBoardCommentSeq") Long freeBoardCommentSeq,
                                @SessionAttribute(name = SessionConst.LOGIN_SESSION_KEY, required = false) SessionForm sessionForm) {

        Optional<FreeBoardComment> findFreeBoardComment = freeBoardCommentRepository.findFreeBoardCommentByFreeBoardCommentSeq(freeBoardCommentSeq);
        if (findFreeBoardComment.isEmpty()) {
            return "redirect:/community/freeBoard/" + freeBoardSeq;
        }

        if (findFreeBoardComment.get().getFreeBoardCommentSid().equals(sessionForm.getSid())) {
            freeBoardCommentRepository.removeFreeBoardCommentByFreeBoardCommentSeq(freeBoardCommentSeq);
        }
        return "redirect:/community/freeBoard/" + freeBoardSeq;
    }

}
