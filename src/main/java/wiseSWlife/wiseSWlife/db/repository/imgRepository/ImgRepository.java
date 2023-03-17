package wiseSWlife.wiseSWlife.db.repository.imgRepository;

import wiseSWlife.wiseSWlife.dto.img.ImgItem;

public interface ImgRepository {

    ImgItem save(ImgItem imgItem);

    ImgItem findImgBySeq(Long manageSeq);

    ImgItem findImgByUUID(String UUID);
}
