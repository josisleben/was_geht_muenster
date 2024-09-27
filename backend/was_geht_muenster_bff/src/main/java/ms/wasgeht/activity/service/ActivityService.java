package ms.wasgeht.activity.service;

import ms.wasgeht.activity.ActivityModel;
import ms.wasgeht.activity.repository.ActivityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ActivityService {

    @Autowired
    private ActivityRepository activityRepository;


    public ActivityModel save(ActivityModel activityModel) {
        return activityRepository.save(activityModel);
    }


}
