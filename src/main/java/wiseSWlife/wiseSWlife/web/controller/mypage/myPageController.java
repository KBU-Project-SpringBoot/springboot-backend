package wiseSWlife.wiseSWlife.web.controller.mypage;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import wiseSWlife.wiseSWlife.config.session.SessionConst;
import wiseSWlife.wiseSWlife.config.session.form.SessionForm;
import wiseSWlife.wiseSWlife.domain.repositoryInterface.communityRepository.auctionBoardRepository.AuctionBoardRepository;
import wiseSWlife.wiseSWlife.domain.repositoryInterface.communityRepository.freeBoardRepository.FreeBoardCommentRepository;
import wiseSWlife.wiseSWlife.domain.repositoryInterface.communityRepository.freeBoardRepository.FreeBoardRepository;

@Slf4j
@Controller
@RequiredArgsConstructor
public class myPageController {

    private final FreeBoardRepository freeBoardRepository;
    private final FreeBoardCommentRepository freeBoardCommentRepository;

    private final AuctionBoardRepository auctionBoardRepository;


    @GetMapping("/myPage")
    public String myPage(@SessionAttribute(name = SessionConst.LOGIN_SESSION_KEY, required = false) SessionForm sessionForm,
                         Model model) {
        model.addAttribute("sessionForm", sessionForm);
        return "myPage/myPage";
    }


    // myPost & myComment Controller
    @GetMapping("/myPage/myPost")
    public String myPost(@SessionAttribute(name = SessionConst.LOGIN_SESSION_KEY, required = false) SessionForm sessionForm,
                         Model model) {
        model.addAttribute("sessionForm", sessionForm);
        model.addAttribute("freeBoards", freeBoardRepository.findFreeBoardBySid(sessionForm.getSid()));
        model.addAttribute("auctionBoards", auctionBoardRepository.findAllAuctionBySid(sessionForm.getSid()));
        log.info("tete ={}",auctionBoardRepository.findAllAuctionBySid(sessionForm.getSid()));

        return "myPage/myPost/myPost";
    }

    @GetMapping("/myPage/myComment")
    public String myComment(@SessionAttribute(name = SessionConst.LOGIN_SESSION_KEY, required = false) SessionForm sessionForm,
                            Model model) {
        model.addAttribute("sessionForm", sessionForm);
        model.addAttribute("freeBoardComments", freeBoardCommentRepository.findAllFreeBoardCommentBySid(sessionForm.getSid()));
        return "myPage/myComment/myComment";
    }

}
