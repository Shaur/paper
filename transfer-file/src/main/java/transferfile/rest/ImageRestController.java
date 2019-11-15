package transferfile.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import ru.comics.mq.MQService;
import ru.comics.mq.configuration.MessageQueueChannels;
import ru.comics.mq.transfer.FileUploadInfo;
import transferfile.utils.HashUtils;

import java.io.File;
import java.util.Objects;

@RestController
public class ImageRestController {


    @Value("${upload.path}")
    private String uploadPath;

    private final MQService mqService;

    @Autowired
    public ImageRestController(MQService mqService) {
        this.mqService = mqService;
    }


    @RequestMapping(value = "/vueupload", method = RequestMethod.POST, produces = MediaType.TEXT_PLAIN_VALUE)
    public String uploadFileREST(@RequestParam MultipartFile file) throws Exception {


        String serverNameFile = null;
        if (!file.isEmpty()) {

            File uploadDir = new File(String.format("%s%s%s", System.getProperty("user.dir"), File.separatorChar, uploadPath));
            checkDir(uploadDir);
            String fileHashMD5 = HashUtils.multiPartFiletomd5(file);
            String fileFormat = Objects.requireNonNull(file.getOriginalFilename()).substring(file.getOriginalFilename().lastIndexOf(".") + 1);
            serverNameFile = String.format("%s.%s", fileHashMD5, fileFormat);
            FileUploadInfo fileUploadInfo = new FileUploadInfo(fileHashMD5,serverNameFile);
            file.transferTo(new File(uploadDir.toPath() + "/" + serverNameFile));
            mqService.publish(MessageQueueChannels.FILE,fileUploadInfo);
            return serverNameFile;
        }
        return serverNameFile;


    }

    private void checkDir(File uploadDir) {
        if (!uploadDir.exists()) {
            uploadDir.mkdirs(

            );
        }
    }


}
