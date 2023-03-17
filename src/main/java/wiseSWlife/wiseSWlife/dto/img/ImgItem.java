package wiseSWlife.wiseSWlife.dto.img;

import lombok.Data;

@Data
public class ImgItem {

    private Long manageSeq; // 이미지 관리 번호

    private String uploadImgName; // 사용자 입장 이미지 이름

    private String storeImgName; // 시스템 내부 저장 이미지 이름

    public ImgItem(String uploadImgName, String storeImgName) {
        this.uploadImgName = uploadImgName;
        this.storeImgName = storeImgName;
    }
}
