package kong.tues.commons.image;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.net.MalformedURLException;

@RequiredArgsConstructor
@Controller
public class ImageController {

    private final ImageOfferManager imageOfferManager;

    @ResponseBody
    @GetMapping("/images/{fileName}")
    public Resource showImage(@PathVariable String fileName) throws MalformedURLException {
        return new UrlResource("file:" + imageOfferManager.getFullPath(fileName));
    }

}
