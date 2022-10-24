package wiseSWlife.wiseSWlife.web.controller.community.auctionBoard;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import wiseSWlife.wiseSWlife.db.repository.imgRepository.ImgRepository;
import wiseSWlife.wiseSWlife.global.session.SessionConst;
import wiseSWlife.wiseSWlife.global.session.form.SessionForm;
import wiseSWlife.wiseSWlife.model.community.auction.Auction;
import wiseSWlife.wiseSWlife.db.repository.communityRepository.auctionBoardRepository.AuctionBoardRepository;
import wiseSWlife.wiseSWlife.model.community.auction.form.AuctionSellForm;
import wiseSWlife.wiseSWlife.model.img.ImgItem;
import wiseSWlife.wiseSWlife.service.img.imgServiceInf.ImgService;

import javax.validation.Valid;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Slf4j
@Controller
@RequiredArgsConstructor
public class AuctionBoardController {
    private final AuctionBoardRepository auctionBoardRepository;
    private final ImgRepository imgRepository;
    private final ImgService imgService;

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
                                       @SessionAttribute(name = SessionConst.LOGIN_SESSION_KEY, required = false)SessionForm sessionForm) throws IOException {
        if(bindingResult.hasErrors()){
            return "community/auctionBoard/AuctionSell";
        }
        //post success logic
        if(!sellForm.getImg().isEmpty()){
            ImgItem imgItem = imgService.saveImg(sellForm.getImg());
            auctionBoardRepository.save(new Auction(sellForm.getSeller(),sessionForm.getSid(), sellForm.getProductName(), sellForm.getPrice(),imgRepository.findImgBySeq(imgItem.getManageSeq()).getStoreImgName(), sellForm.getText(), new Date().toString()));
        }else{
            auctionBoardRepository.save(new Auction(sellForm.getSeller(),sessionForm.getSid(), sellForm.getProductName(), sellForm.getPrice(),null, sellForm.getText(), new Date().toString()));
        }

        return "redirect:/community/auctionBoard";
    }

    @GetMapping("community/auctionBoard/{auctionSeq}")
    public String requestAuctionBoard(@PathVariable("auctionSeq")Long auctionSeq,
                                      @SessionAttribute(name = SessionConst.LOGIN_SESSION_KEY, required = false)SessionForm sessionForm,
                                      Model model) {

        Optional<Auction> findAuction = auctionBoardRepository.findAuctionBySeq(auctionSeq);
        if(findAuction.isEmpty()){
            log.error("acess to nonexistent AuctionSeq = {}",auctionSeq);
            return "redirect:/community/auctionBoard";
        }

        //success logic
        model.addAttribute("auctionBoard", findAuction.get());
        model.addAttribute("sessionSid", sessionForm.getSid());
        return "community/auctionBoard/RequestAuctionBoard";

    }

    @GetMapping("/community/auctionBoard/update/{auctionSeq}")
    public String requestAuctionUpdate(@SessionAttribute(name = SessionConst.LOGIN_SESSION_KEY) SessionForm form,
                                       @PathVariable("auctionSeq")Long auctionSeq,
                                       Model model) {
        Optional<Auction> findAuction = auctionBoardRepository.findAuctionBySeq(auctionSeq);
        if(findAuction.isEmpty()){
            return "redirect:/community/auctionBoard";
        }
        Auction pastAuction = findAuction.get();

        model.addAttribute("auctionSellForm",new AuctionSellForm(pastAuction.getSeller(),pastAuction.getProductName(),null,pastAuction.getText(), Math.toIntExact(pastAuction.getPrice())));
        return "community/auctionBoard/AuctionUpdate";
    }

    @PostMapping ("/community/auctionBoard/update/{auctionSeq}")
    public String AuctionUpdate(@SessionAttribute(name = SessionConst.LOGIN_SESSION_KEY) SessionForm sessionForm,
                                @PathVariable("auctionSeq")Long auctionSeq,
                                @Valid @ModelAttribute("auctionSellForm")AuctionSellForm updatedAuction, BindingResult bindingResult) throws IOException {
        Optional<Auction> findAuction = auctionBoardRepository.findAuctionBySeq(auctionSeq);
        if(findAuction.isEmpty()){
            return "redirect:/community/auctionBoard";
        }
        if(bindingResult.hasErrors()){
            return "community/auctionBoard/AuctionUpdate";
        }

        Auction auction = findAuction.get();
        if (sessionForm.getSid().equals(auction.getSellerSid())) {
            if(!updatedAuction.getImg().isEmpty()){
                ImgItem imgItem = imgService.saveImg(updatedAuction.getImg());
                auction.setImgUrl(imgItem.getStoreImgName());
            }

            auction.setProductName(updatedAuction.getProductName());
            auction.setPrice(updatedAuction.getPrice());
            auction.setText(updatedAuction.getText());
            auction.setSeller(updatedAuction.getSeller());
            auction.setDate(new Date().toString());

        }
        return "redirect:/community/auctionBoard/" + auctionSeq;
    }

    @GetMapping("/community/auctionBoard/remove/{auctionSeq}")
    public String auctionRemove(@PathVariable("auctionSeq") Long auctionSeq,
                                @SessionAttribute(name = SessionConst.LOGIN_SESSION_KEY, required = false) SessionForm sessionForm) {
        Optional<Auction> findAuction = auctionBoardRepository.findAuctionBySeq(auctionSeq);

        if(findAuction.isEmpty()){
            return "redirect:/community/auctionBoard";
        }
        if(findAuction.get().getSellerSid().equals(sessionForm.getSid())){
            auctionBoardRepository.removeAuction(auctionSeq);
        }
        return "redirect:/community/auctionBoard";
    }
}
