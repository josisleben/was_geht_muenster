package ms.wasgeht.activity.service;

import ms.wasgeht.activity.ActivityModel;
import ms.wasgeht.activity.repository.ActivityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ActivityService {

    @Autowired
    private ActivityRepository activityRepository;


    public ActivityModel save(ActivityModel activityModel) {
        return activityRepository.save(activityModel);
    }

    public ActivityModel findById(UUID id) {
        return activityRepository.findById(id).orElse(null);
    }

    public List<ActivityModel> findAll() {
        return activityRepository.findAll();
    }


}
