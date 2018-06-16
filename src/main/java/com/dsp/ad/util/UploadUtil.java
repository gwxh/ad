package com.dsp.ad.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;

@Service
public class UploadUtil {

    @Value("${upload-image-path}")
    private String path;

    public String uploadImage(MultipartFile imageFile) throws IOException {
        if (imageFile != null) {
            String time = TimeUtil.getYYYYMMDDhhmmss(LocalDateTime.now());
            String newImageName = time + "_" + imageFile.getOriginalFilename();
            imageFile.transferTo(new File(path + newImageName));
            return newImageName;
        }
        return null;
    }
}
