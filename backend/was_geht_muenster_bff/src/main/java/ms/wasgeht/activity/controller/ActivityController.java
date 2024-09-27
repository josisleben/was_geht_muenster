package ms.wasgeht.activity.controller;

import ms.wasgeht.activity.ActivityModel;
import ms.wasgeht.activity.repository.ActivityRepository;
import ms.wasgeht.activity.service.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/activities")
public class ActivityController {

    @Autowired
    private ActivityRepository activityRepository;

    @GetMapping
    public List<ActivityModel> getAllActivities() {
        return activityRepository.findAll();
    }

    @GetMapping("/{id}")
    public ActivityModel getActivityById(@PathVariable Long id) {
        return activityRepository.findById(id).orElse(null);
    }

    @PostMapping
    public ActivityModel createActivity(@RequestBody ActivityModel activityModel) {
        return activityRepository.save(activityModel);
    }

}
