package ms.wasgeht.activity.controller;

import ms.wasgeht.activity.ActivityModel;
import ms.wasgeht.activity.dto.ActivityRequestDto;
import ms.wasgeht.activity.repository.ActivityRepository;
import ms.wasgeht.activity.service.ActivityService;
import ms.wasgeht.exceptions.AbstractMSHackException;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin
@RequestMapping("/api/activities")
public class ActivityController {

    @Autowired
    private ActivityService activityService;

    @GetMapping
    public ResponseEntity<String> getAllActivities(@RequestParam(defaultValue = "1") final int page, @RequestParam(defaultValue = "10") final int size) {
        return this.activityService.getActivities(page, size).toResponse(HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<String> getActivityById(@PathVariable UUID id) {
        try{
            return this.activityService.getActivity(id).toResponse(HttpStatus.OK);
        }catch (AbstractMSHackException e){
            return e.toResult();
        }
    }

    @PostMapping
    public ResponseEntity<String> createActivity(@RequestBody ActivityRequestDto request) {
        try{
            this.activityService.createActivity(request);
            return new ResponseEntity("Created", HttpStatus.CREATED);
        }catch (AbstractMSHackException e){
            return e.toResult();
        }
    }

}
