package wiseSWlife.wiseSWlife.service.img.imgServiceInf;

import org.springframework.web.multipart.MultipartFile;
import wiseSWlife.wiseSWlife.dto.img.ImgItem;

import java.io.IOException;

public interface ImgService {

    String getFullPath(String fileName);

    String createStoreImgName(String originalName);

    String extractExt(String originalFileName);

    ImgItem saveImg(MultipartFile file) throws IOException;



}
