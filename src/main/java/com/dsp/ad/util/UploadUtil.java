package com.dsp.ad.util;

import cn.hutool.core.util.RandomUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class UploadUtil {

    @Value("${file.image.path}")
    private String filePath;

    @Value("${file.url}")
    private String fileUrl;

    public String upload(MultipartFile file) {
        String originalFilename = file.getOriginalFilename();
        String extension = "." + FilenameUtils.getExtension(originalFilename);
        Date now = new Date();
        String newFileName = new SimpleDateFormat("yyyyMMddHHmmss").format(now)
                + RandomUtil.randomString(6)
                + extension;

        File dataFile = new File(filePath);  //也可以直接放进去进行拼接 File dataFile = new File(path,format);
        if (!dataFile.exists()) {
            dataFile.mkdirs();
        }

        //文件上传至指定路径
        try {
            file.transferTo(new File(dataFile, newFileName));
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
        return fileUrl + newFileName;
    }


}
