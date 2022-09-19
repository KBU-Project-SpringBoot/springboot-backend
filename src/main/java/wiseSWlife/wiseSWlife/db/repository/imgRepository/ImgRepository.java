package wiseSWlife.wiseSWlife.db.repository.imgRepository;

import wiseSWlife.wiseSWlife.model.img.ImgItem;

public interface ImgRepository {

    ImgItem save(ImgItem imgItem);

    ImgItem findImgBySeq(Long manageSeq);

    ImgItem findImgByUUID(String UUID);
}
