package ms.wasgeht.activity.category.controller;

import ms.wasgeht.activity.category.ActivityCategoryType;
import ms.wasgeht.activity.category.dto.ActivityCategoryListResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

@RestController
@CrossOrigin
@RequestMapping("/api/category")
public class ActivityCategoryController {

    @GetMapping
    public ResponseEntity<?> getCategories() {
        return ActivityCategoryListResponseDto.builder().categories(Arrays.stream(ActivityCategoryType.values()).map(ActivityCategoryType::toResponse).toList()).build().toResponse(HttpStatus.OK);
    }
}
