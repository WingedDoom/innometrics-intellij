package services.impl;

import models.Activity;
import services.ActivitiesStorageService;

import java.util.Collections;
import java.util.List;

public class ActivitiesStorageServiceImpl implements ActivitiesStorageService {
    @Override
    public List<Activity> getActivitiesBatch(int maxBatchSize) {
        return Collections.emptyList();
    }

    @Override
    public void removeActivities(List<Activity> activitiesToRemove) {

    }

    @Override
    public void saveActivities(List<Activity> newActivities) {

    }
}
