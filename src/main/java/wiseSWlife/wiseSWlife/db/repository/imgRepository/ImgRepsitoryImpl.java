package wiseSWlife.wiseSWlife.db.repository.imgRepository;

import org.springframework.stereotype.Repository;
import wiseSWlife.wiseSWlife.dto.img.ImgItem;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
public class ImgRepsitoryImpl implements ImgRepository {
    private final Map<Long, ImgItem> store = new HashMap<>();
    private long manageSeq = 0L;
    @Override
    public ImgItem save(ImgItem imgItem) {
        imgItem.setManageSeq(++manageSeq);
        store.put(imgItem.getManageSeq(), imgItem);
        return imgItem;
    }

    @Override
    public ImgItem findImgBySeq(Long manageSeq) {
        return store.get(manageSeq);
    }

    @Override
    public ImgItem findImgByUUID(String imgUUID) {
        Optional<ImgItem> img = store.values().stream()
                .filter(i -> i.getStoreImgName().equals(imgUUID))
                .findFirst();
        if(img.isEmpty()){
            return null;
        }
        return img.get();
    }
}
