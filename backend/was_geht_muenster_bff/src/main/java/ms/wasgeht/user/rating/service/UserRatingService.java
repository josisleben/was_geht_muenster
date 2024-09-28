package ms.wasgeht.user.rating.service;

import ms.wasgeht.exceptions.AbstractMSHackException;
import ms.wasgeht.exceptions.MissingFieldException;
import ms.wasgeht.exceptions.user.UserRatingAlreadyExistsException;
import ms.wasgeht.exceptions.user.UserRatingInvalidDataException;
import ms.wasgeht.user.UserModel;
import ms.wasgeht.user.rating.UserRating;
import ms.wasgeht.user.rating.dto.UserAverageRatingResponseDto;
import ms.wasgeht.user.rating.dto.UserRatingListResponseDto;
import ms.wasgeht.user.rating.dto.UserRatingRequestDto;
import ms.wasgeht.user.rating.dto.UserRatingResponseDto;
import ms.wasgeht.user.rating.repository.UserRatingRepository;
import ms.wasgeht.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserRatingService {

    @Autowired
    private UserRatingRepository userRatingRepository;
    @Autowired
    private UserService userService;

    private boolean exists(final UUID id){
        return this.userRatingRepository.existsById(id);
    }

    public void save(UserRating userRating){
        this.userRatingRepository.save(userRating);
    }

    public void createRating(UserModel requestedBy, UserRatingRequestDto request) throws AbstractMSHackException {
        if(request.getComment() == null)
            throw new MissingFieldException("content");
        if(request.getTargetId() == null)
            throw new MissingFieldException("targetId");
        final UserModel targetUser = this.userService.getUserById(request.getTargetId());
        if(this.userRatingRepository.existsByTargetIdEqualsAndCreatorIdEquals(targetUser.getId(), requestedBy.getId()))
            throw new UserRatingAlreadyExistsException();
        if(request.getRating() < 0 || request.getRating() > 10)
            throw new UserRatingInvalidDataException("Rating must be in range from 0 to 10");
        UserRating userRating = UserRating.builder().id(this.generateFreeId())
                .creatorId(requestedBy.getId())
                .targetId(targetUser.getId())
                .rating(ConvertToRoundedRating(request.getRating()))
                .comment(request.getComment()).build();
        this.userRatingRepository.save(userRating);
    }

    public UserRatingListResponseDto getRatingsFromUser(UserModel targetUser, int page, int size){
        return UserRatingListResponseDto.builder()
                .ratings(convertUserRating(this.userRatingRepository.findAllByTargetIdEquals(targetUser.getId(), PageRequest.of(page - 1, size)).getContent())).build();
    }

    public UserAverageRatingResponseDto getAvgRatingFromUser(UserModel targetUser){
        return UserAverageRatingResponseDto.builder().avgRating(this.userRatingRepository.findAllByTargetIdEquals(targetUser.getId()).stream().mapToDouble(UserRating::getRating).average().orElse(0)).build();
    }

    private UserRatingResponseDto convertUserRating(UserRating rating){
        return UserRatingResponseDto.builder().id(rating.getId()).creatorId(rating.getCreatorId()).comment(rating.getComment()).rating(rating.getRating()).build();
    }

    private List<UserRatingResponseDto> convertUserRating(List<UserRating> rating){
        return rating.stream().map(this::convertUserRating).toList();
    }

    private static double ConvertToRoundedRating(double rating){
        final double max = Math.ceil(rating);
        final double min = Math.floor(rating);
        final double half = Math.floor(rating) + 0.5;

        final double minDistance = Math.abs(rating - min);
        final double maxDistance = Math.abs(max - rating);
        final double halfDistance = Math.abs(rating - half);

        if(maxDistance <= halfDistance)
            return max;
        if(halfDistance <= minDistance)
            return half;
        return min;
    }

    private UUID generateFreeId(){
        UUID id;
        do{
            id = UUID.randomUUID();
        }while(this.exists(id));
        return id;
    }

}
