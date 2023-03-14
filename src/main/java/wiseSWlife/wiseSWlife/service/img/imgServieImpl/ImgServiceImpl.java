package wiseSWlife.wiseSWlife.service.img.imgServieImpl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import wiseSWlife.wiseSWlife.db.repository.imgRepository.ImgRepository;
import wiseSWlife.wiseSWlife.dto.img.ImgItem;
import wiseSWlife.wiseSWlife.service.img.imgServiceInf.ImgService;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ImgServiceImpl implements ImgService {
    @Value("${file.dir}")
    private String fileDir;

    private final ImgRepository imgRepository;

    @Override
    public String getFullPath(String fileName) {
        return fileDir + fileName;
    }

    @Override
    public String createStoreImgName(String originalName) {
        String ext = extractExt(originalName);
        String uuid = UUID.randomUUID().toString();
        return uuid+"."+ext;
    }

    @Override
    public String extractExt(String originalFileName) {
        int pos = originalFileName.lastIndexOf(".");
        return originalFileName.substring(pos + 1);
    }

    @Override
    public ImgItem saveImg(MultipartFile multipartFile) throws IOException {
        if(multipartFile.isEmpty()){
            return null;
        }
        String originalFilename = multipartFile.getOriginalFilename();
        String storeImgName = createStoreImgName(originalFilename);
        multipartFile.transferTo(new File(getFullPath(storeImgName)));
        ImgItem imgItem = new ImgItem(originalFilename, storeImgName);
        imgRepository.save(imgItem);
        return imgItem;
    }
}
