package wiseSWlife.wiseSWlife.model.community.auction.form;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.lang.Nullable;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.io.File;

@Data
@AllArgsConstructor
public class AuctionSellForm {
    @NotEmpty
    @NotNull
    private String seller;

    @NotEmpty
    @NotNull
    private String productName;


    private MultipartFile img;

    @NotEmpty
    @NotNull
    private String Text;

    @Nullable
    @PositiveOrZero
    private int price;

}
