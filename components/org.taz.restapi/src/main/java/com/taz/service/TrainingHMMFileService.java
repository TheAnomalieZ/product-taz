package com.taz.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.taz.core.autoencoder.AE;
import org.taz.core.autoencoder.AETrain;
import org.taz.core.hmm.HMMTrain;

import java.io.File;
import java.util.ArrayList;
import java.util.logging.Logger;

/**
 * @author kokulan k
 * @Created on 11/28/2016
 */
@Service
public class TrainingHMMFileService {
    private static Logger log = Logger.getLogger(FileService.class.getName());

    @Value("${service.file.train_path_hmm}")
    private String rootPath;

    public String uploadJFR(String ext, MultipartFile file) {
        try {
            // Creating the directory to store file
            File dir = new File(rootPath + File.separator);
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

    public ArrayList<String> getAllFileNames(){
        ArrayList<String> fileNames = new ArrayList<>();
        File folder = new File(rootPath);
        File [] files = folder.listFiles();
        for(File file: files){
            fileNames.add(file.getName());
        }
        return fileNames;
    }

    public boolean deleteFile(String fileName){
        boolean deleted = false;
        try{
            File file = new File(rootPath+"/"+fileName);
            deleted = file.delete();
        } catch (Exception e){

        }
        return deleted;
    }

    @Async
    public boolean aeTrain(String fileName){
        boolean train = false;
        HMMTrain hmmTrain = new HMMTrain();
        String filePath = rootPath + "/" + fileName;
        train = hmmTrain.startHMMTraining(filePath, "ap3");

        return train;
    }
}
