package ms.wasgeht.user.controller;

import lombok.Getter;
import ms.wasgeht.exceptions.AbstractMSHackException;
import ms.wasgeht.user.dto.UserResponseDto;
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
public class UserGeneralController {

    @Autowired
    private UserService userService;

    @GetMapping("/{userId}")
    public ResponseEntity<String> onGetUser(final @RequestHeader(name = "Authorization", required = false) String sessionToken, @PathVariable final UUID userId){
        try{
            UserResponseDto responseDto;
            if(sessionToken == null || sessionToken.isEmpty() || sessionToken.isBlank())
                responseDto = userService.getUser(userId);
            else
                responseDto = userService.getUser(this.userService.getUserBySessionToken(sessionToken), userId);
            return responseDto.toResponse(HttpStatus.OK);
        }catch (AbstractMSHackException e){
            return e.toResult();
        }
    }

    @GetMapping("/{userId}/avatar")
    public ResponseEntity<byte[]> onAvatar(@PathVariable final UUID userId){
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_PNG);
        return new ResponseEntity<>(this.userService.getAvatar(userId), headers, HttpStatus.OK);
    }

}
