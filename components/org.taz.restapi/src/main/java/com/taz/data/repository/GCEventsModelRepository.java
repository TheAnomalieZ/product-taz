package com.taz.data.repository;

import com.taz.data.entity.GCEventsModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by K.Kokulan on 1/11/2017.
 */
@Repository
public interface GCEventsModelRepository extends MongoRepository<GCEventsModel, String> {

    GCEventsModel findGCEventsModelByFileName(String fileName);
}
