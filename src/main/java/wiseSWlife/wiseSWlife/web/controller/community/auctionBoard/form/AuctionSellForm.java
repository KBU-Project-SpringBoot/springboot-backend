package wiseSWlife.wiseSWlife.web.controller.community.auctionBoard.form;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

@Data
public class AuctionSellForm {
    @NotEmpty
    @NotNull
    private String seller;

    @NotEmpty
    @NotNull
    private String productName;

    @NotEmpty
    @NotNull
    private String Text;


    @NotNull
    @PositiveOrZero
    private int price;

}
