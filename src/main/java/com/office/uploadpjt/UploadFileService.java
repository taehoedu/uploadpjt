package com.office.uploadpjt;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.UUID;

@Service
public class UploadFileService {

    @Value("${file.upload-dir}")
    private String uploadDir;

    public String upload(MultipartFile file) {
        System.out.println("[UploadFileService] upload()");

        boolean result = false;

        String fileOriName = file.getOriginalFilename();
        String fileExtension = fileOriName.substring(fileOriName.lastIndexOf("."), fileOriName.length());

        UUID uuid = UUID.randomUUID();
        String uniqueFileName = uuid.toString().replaceAll("-", "");

        File dir = new File(uploadDir);
        if (!dir.exists())
            dir.mkdirs();

        // For windows
        File saveFile = new File(uploadDir + "\\" + uniqueFileName + fileExtension);

        // For linux(ubuntu)
        // File saveFile = new File(uploadDir + "/" + uniqueFileName + fileExtension);

        try {
            file.transferTo(saveFile);
            result = true;

        } catch (Exception e) {
            e.printStackTrace();

        }

        if (result) {
            System.out.println("[UploadFileService] FILE UPLOAD SUCCESS!!");
            System.out.println("[UploadFileService] FILE NAME: " + uniqueFileName + fileExtension);
            return uniqueFileName + fileExtension;

        } else {
            System.out.println("[UploadFileService] FILE UPLOAD FAIL!!");
            return null;

        }

    }

}
