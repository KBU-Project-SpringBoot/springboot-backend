package wiseSWlife.wiseSWlife.web.controller.img;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import wiseSWlife.wiseSWlife.service.img.ImgService;

import java.net.MalformedURLException;

@Controller
@RequiredArgsConstructor
public class ImageController {
    private final ImgService imgService;

    @ResponseBody
    @GetMapping("/images/{imgUrl}")
    public Resource image(@PathVariable String imgUrl) throws MalformedURLException {
        return new UrlResource("file:" + imgService.getFullPath(imgUrl));

    }

}
