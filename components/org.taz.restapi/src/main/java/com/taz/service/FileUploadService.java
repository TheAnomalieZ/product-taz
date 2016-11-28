package com.taz.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.UUID;
import java.util.logging.Logger;

/**
 * @author kokulan k
 * @Created on 11/28/2016
 */
@Service
public class FileUploadService {
    private static Logger log = Logger.getLogger(FileUploadService.class.getName());

    @Value("${service.file.path}")
    private String rootPath;

    public String uploadJFR(String ext, MultipartFile file) {
        try {
            // Creating the directory to store file
            File dir = new File(rootPath + File.separator);
//            String nameNew = UUID.randomUUID().toString() + '.' + ext;
            String nameNew = file.getOriginalFilename();
            // Create the file on server
            File serverFile = new File(dir.getAbsolutePath()
                    + File.separator + nameNew);

            file.transferTo(serverFile);

            return nameNew;
        } catch (Exception e) {
            return null;
        }
    }
}
