package ms.wasgeht.activity.category;

import lombok.AllArgsConstructor;
import lombok.Getter;
import ms.wasgeht.activity.category.dto.ActivityCategoryResponseDto;
import ms.wasgeht.activity.dto.ActivityResponseDto;

@AllArgsConstructor
@Getter
public enum ActivityCategoryType {
    SPORT("Sport"),
    CULTURE("Kultur"),
    CHARITY("Ehrenamtlich"),
    MISC("Verschiedenes"),
    GAMES("Spiele");

    private final String name;

    public ActivityCategoryResponseDto toResponse(){
        return ActivityCategoryResponseDto.builder().id(this.toString()).name(this.name).build();
    }
}
