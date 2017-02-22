package com.taz.data;

import com.taz.data.entity.GCEventsModel;
import com.taz.data.repository.GCEventsModelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * Created by K.Kokulan on 1/11/2017.
 */
@Service
public class GCEventModelService {

    @Qualifier("GCEventsModelRepository")
    @Autowired
    GCEventsModelRepository gcEventsModelRepository;

    public void save(GCEventsModel gcEventsModel){
        gcEventsModelRepository.save(gcEventsModel);
    }

    public GCEventsModel findGCEventsModelByFileName(String fileName){
        GCEventsModel gcEventsModel = gcEventsModelRepository.findGCEventsModelByFileName(fileName);
        if(gcEventsModel == null){
            return null;
        }

        return gcEventsModel;
    }

}
