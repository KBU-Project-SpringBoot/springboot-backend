package wiseSWlife.wiseSWlife.domain.community.auction;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Data
@Getter @Setter
public class Auction {

    //    판매 번호
    private Long auctionSeq;
    //    판매자 닉네임
    private String seller;
    // 판매자 학번
    private String sellerSid;
    //    상품 이름
    private String productName;
    //    상품 가격
    private Integer price;
    //    상품 설명
    private String Text;
    //    상품 이미지 경로
    private String imgUrl;
    //    판매 시간
    private Date date;
    //    판매완료 여부
    private boolean soldOut;

    public Auction(String seller,String sellerSid, String productName, int price, String text, Date date) {
        this.seller = seller;
        this.productName = productName;
        this.price = price;
        this.sellerSid = sellerSid;
        this.Text = text;
        this.date = date;
    }
}
