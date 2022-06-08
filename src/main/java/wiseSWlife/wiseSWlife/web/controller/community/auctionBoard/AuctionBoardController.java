package wiseSWlife.wiseSWlife.web.controller.community.auctionBoard;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import wiseSWlife.wiseSWlife.config.session.SessionConst;
import wiseSWlife.wiseSWlife.config.session.form.SessionForm;
import wiseSWlife.wiseSWlife.domain.community.auction.Auction;
import wiseSWlife.wiseSWlife.domain.repositoryInterface.communityRepository.auctionBoardRepository.AuctionBoardRepository;
import wiseSWlife.wiseSWlife.web.controller.community.auctionBoard.form.AuctionSellForm;
import wiseSWlife.wiseSWlife.web.controller.community.freeBoard.form.FreeBoardCommentForm;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Slf4j
@Controller
@RequiredArgsConstructor
public class AuctionBoardController {
    private final AuctionBoardRepository auctionBoardRepository;

    @GetMapping("/community/auctionBoard")
    public String auctionBoard(@SessionAttribute(name = SessionConst.LOGIN_SESSION_KEY, required = false) SessionForm sessionForm,
                          Model model) {
        List<Auction> allAuctionBoards = auctionBoardRepository.findAllAuction();
        model.addAttribute("auctionBoards", allAuctionBoards);
        model.addAttribute("sessionForm", sessionForm);

        return "community/auctionBoard/Auction";
    }

    @GetMapping("/community/auctionBoard/sell")
    public String getAuctionBoardSell(@ModelAttribute("auctionSellForm")AuctionSellForm sellForm) {
        return "community/auctionBoard/AuctionSell";

    }

    @PostMapping("/community/auctionBoard/sell")
    public String postAuctionBoardSell(@Valid @ModelAttribute("auctionSellForm")AuctionSellForm sellForm,
                                       BindingResult bindingResult,
                                       @SessionAttribute(name = SessionConst.LOGIN_SESSION_KEY, required = false)SessionForm sessionForm) {
        if(bindingResult.hasErrors()){
            return "community/auctionBoard/AuctionSell";
        }
        //post success logic
        auctionBoardRepository.save(new Auction(sellForm.getSeller(),sessionForm.getSid(), sellForm.getProductName(), Long.valueOf(sellForm.getPrice()), sellForm.getText(), new Date()));
        return "redirect:/community/auctionBoard";
    }

    @GetMapping("community/auctionBoard/{auctionSeq}")
    public String requestAuctionBoard(@PathVariable("auctionSeq")Long auctionSeq,
                                      Model model) {

        Optional<Auction> findAuction = auctionBoardRepository.findAuctionBySeq(auctionSeq);
        if(findAuction.isEmpty()){
            log.error("acess to nonexistent AuctionSeq = {}",auctionSeq);
            return "redirect:/community/auctionBoard";
        }

        //success logic
        model.addAttribute("auctionBoard", findAuction.get());
        return "community/auctionBoard/RequestAuctionBoard";

    }
}
