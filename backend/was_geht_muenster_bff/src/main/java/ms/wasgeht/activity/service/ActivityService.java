package ms.wasgeht.activity.service;

import ms.wasgeht.WasGehtMuensterBffApplication;
import ms.wasgeht.activity.ActivityModel;
import ms.wasgeht.activity.category.ActivityCategoryType;
import ms.wasgeht.activity.dto.ActivityListResponseDto;
import ms.wasgeht.activity.dto.ActivityRequestDto;
import ms.wasgeht.activity.dto.ActivityResponseDto;
import ms.wasgeht.activity.repository.ActivityRepository;
import ms.wasgeht.exceptions.AbstractMSHackException;
import ms.wasgeht.exceptions.MissingFieldException;
import ms.wasgeht.exceptions.activity.ActivityAlreadyExistsException;
import ms.wasgeht.exceptions.activity.ActivityNotFoundException;
import ms.wasgeht.util.AbstractResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Base64;
import java.util.List;
import java.util.UUID;

@Service
public class ActivityService {

    @Autowired
    private ActivityRepository activityRepository;


    public ActivityModel save(ActivityModel activityModel) {
        return activityRepository.save(activityModel);
    }

    public ActivityModel findById(UUID id) throws ActivityNotFoundException {
        return activityRepository.findById(id).orElseThrow(ActivityNotFoundException::new);
    }


    /*public ActivityModel getActivityByName(final String name) throws ActivityAlreadyExists {
        return this.activityRepository.findByNameEqualsIgnoreCase(name).orElseThrow(ActivityAlreadyExists::new);
    }*/

    public boolean existsByName(final String name){
        return activityRepository.existsByNameEqualsIgnoreCase(name);
    }

    public boolean exists(final UUID id){
        return this.activityRepository.existsById(id);
    }

    public void createActivity(ActivityRequestDto request) throws AbstractMSHackException {
        if(request.getName() == null || request.getName().isBlank() || request.getName().isEmpty())
            throw new MissingFieldException("Name");
        if(request.getCategory() == null)
            throw new MissingFieldException("Category");
        if(this.existsByName(request.getName()))
            throw new ActivityAlreadyExistsException();
        ActivityModel activityModel = ActivityModel.builder().id(this.generateFreeId())
                .category(request.getCategory()).name(request.getName()).avatar(Base64.getDecoder().decode(request.getAvatar())).build();
        this.save(activityModel);
    }

    public ActivityResponseDto getActivity(UUID id) throws ActivityNotFoundException {
        ActivityModel activityModel = this.findById(id);
        return this.convertActivity(activityModel);
    }

    private UUID generateFreeId(){
        UUID id;
        do{
            id = UUID.randomUUID();
        }while(this.exists(id));
        return id;
    }

    public ActivityListResponseDto getActivities(final ActivityCategoryType category,final int page, final int size) {
        if(category == null)
            return ActivityListResponseDto.builder().activities(this.activityRepository.findAll(PageRequest.of(page - 1, size)).getContent().stream().map(this::convertActivity).toList()).build();
        else
            return ActivityListResponseDto.builder().activities(this.activityRepository.findAllByCategoryEquals(category, PageRequest.of(page - 1, size)).getContent().stream().map(this::convertActivity).toList()).build();
    }

    private ActivityResponseDto convertActivity(final ActivityModel activity) {
        return ActivityResponseDto.builder().name(activity.getName()).category(activity.getCategory()).id(activity.getId()).avatar("https://api.wasgehtmuenster.xyz:8080/api/activities/" + activity.getId() + "/avatar").build();
    }

    public List<ActivityModel> getActivitiesByCategory(final ActivityCategoryType category){
        return this.activityRepository.findAllByCategory(category);
    }

    public byte[] getAvatar(UUID id) {
        try{
            ActivityModel model = this.findById(id);
            if(model.getAvatar() == null)
                return WasGehtMuensterBffApplication.getDefaultActivity();
            return model.getAvatar();
        }catch (ActivityNotFoundException e){
            return WasGehtMuensterBffApplication.getDefaultActivity();
        }
    }
}
