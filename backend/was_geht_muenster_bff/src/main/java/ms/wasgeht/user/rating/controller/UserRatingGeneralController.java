package ms.wasgeht.user.rating.controller;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import ms.wasgeht.exceptions.AbstractMSHackException;
import ms.wasgeht.session.dto.SessionRequestDto;
import ms.wasgeht.user.rating.dto.UserRatingRequestDto;
import ms.wasgeht.user.rating.service.UserRatingService;
import ms.wasgeht.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("api/v1/user")
@CrossOrigin
public class UserRatingGeneralController {

    @Autowired
    private UserService userService;
    @Autowired
    private UserRatingService userRatingService;

    @PostMapping("/{userId}/rating")
    public ResponseEntity<String> onRating(@PathVariable final UUID userId, final @RequestHeader(name = "Authorization") String sessionToken, @RequestBody UserRatingRequestDto requestDto) {
        try{
            this.userRatingService.createRating(userService.getUserBySessionToken(sessionToken), requestDto);
            return new ResponseEntity<>("Created", HttpStatus.CREATED);
        }catch (AbstractMSHackException e){
            return e.toResult();
        }
    }

    @GetMapping("/{userId}/rating")
    public ResponseEntity<String> onRating(@PathVariable final UUID userId, @RequestParam(defaultValue = "1") final int page, @RequestParam(defaultValue = "10") final int size) {
        try{
            return this.userRatingService.getRatingsFromUser(this.userService.getUserById(userId), page, size).toResponse(HttpStatus.OK);
        }catch (AbstractMSHackException e){
            return e.toResult();
        }
    }

    @GetMapping("/{userId}/rating/avg")
    public ResponseEntity<String> onRatingAverage(@PathVariable final UUID userId) {
        try{
            return this.userRatingService.getAvgRatingFromUser(this.userService.getUserById(userId)).toResponse(HttpStatus.OK);
        }catch (AbstractMSHackException e){
            return e.toResult();
        }
    }

}
