package com.taz.controller;

/**
 * Created by K.Kokulan on 11/28/2016.
 */

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.taz.service.FileUploadService;
import com.taz.util.Response;
import com.taz.util.ResponseStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/file")
public class FileController {


    @Autowired
    FileUploadService fileUploadService;

    private ObjectMapper objectMapper = new ObjectMapper(new JsonFactory());;

    @RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
    public String uploadFile(@RequestParam("file") MultipartFile file) throws JsonProcessingException {

        Response<String> response = new Response<>();

        if (!file.isEmpty()) {
            String fileType = file.getContentType();
            String[] content = fileType.split("/");
            if (content[0].equalsIgnoreCase("application") && file.getOriginalFilename().toLowerCase().contains(".jfr")) {
                String id = fileUploadService.uploadJFR(content[1], file);
                if (id != null) {
                    response.setContent(id);
                    response.setType("jfr");
                } else {
                    response.setStatus(ResponseStatus.SERVER_ERROR);
                }
            }
        } else {
            response.setStatus(ResponseStatus.EMPTY_FILE);
        }
        return objectMapper.writeValueAsString(response);
    }
}
