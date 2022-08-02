package wiseSWlife.wiseSWlife.model.community.auction.form;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.lang.Nullable;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

@Data
@AllArgsConstructor
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

    @Nullable
    @PositiveOrZero
    private int price;

}
