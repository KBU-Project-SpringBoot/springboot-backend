package wiseSWlife.wiseSWlife.web.controller.community.freeBoard;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttribute;
import wiseSWlife.wiseSWlife.config.session.SessionConst;
import wiseSWlife.wiseSWlife.config.session.form.SessionForm;
import wiseSWlife.wiseSWlife.domain.community.freeBoard.FreeBoard;
import wiseSWlife.wiseSWlife.domain.repositoryInterface.communityRepository.freeBoardRepository.FreeBoardRepository;

import java.util.List;

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

}
