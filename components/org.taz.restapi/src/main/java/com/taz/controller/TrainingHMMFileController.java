package com.taz.controller;

/**
 * Created by K.Kokulan on 11/28/2016.
 */

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.taz.service.FileService;
import com.taz.service.TrainingFileService;
import com.taz.service.TrainingHMMFileService;
import com.taz.util.Response;
import com.taz.util.ResponseStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/train_file_hmm")
public class TrainingHMMFileController {


    @Autowired
    TrainingHMMFileService fileService;

    private ObjectMapper objectMapper = new ObjectMapper(new JsonFactory());;

    @RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
    public String uploadFile(@RequestParam("file") MultipartFile file) throws JsonProcessingException {

        Response<String> response = new Response<>();

        if (!file.isEmpty()) {
            String fileType = file.getContentType();
            String[] content = fileType.split("/");
            if (content[0].equalsIgnoreCase("application") && file.getOriginalFilename().toLowerCase().contains(".jfr")) {
                String id = fileService.uploadJFR(content[1], file);
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


    @RequestMapping(value = "/deleteFile", method = RequestMethod.GET)
    public boolean deleteFile(@RequestParam("fileName") String fileNeme){
        boolean deleted = false;
        if (!fileNeme.isEmpty()) {
            deleted = fileService.deleteFile(fileNeme);
        }
        return deleted;
    }

    @RequestMapping(value = "/hmm_train", method = RequestMethod.GET)
    public boolean hmmTrain(@RequestParam("fileName") String fileNeme){
        boolean train = false;
        if (!fileNeme.isEmpty()) {
            train = fileService.aeTrain(fileNeme);
        }
        return train;
    }
}
