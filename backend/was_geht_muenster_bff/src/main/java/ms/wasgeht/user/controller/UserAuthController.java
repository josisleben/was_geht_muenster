package ms.wasgeht.user.controller;

import ms.wasgeht.exceptions.AbstractMSHackException;
import ms.wasgeht.user.dto.UserLoginRequestDto;
import ms.wasgeht.user.dto.UserLoginResponseDto;
import ms.wasgeht.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/user/auth")
@CrossOrigin
public class UserAuthController {

    @Autowired
    private UserService userService;

    @PostMapping("")
    public ResponseEntity<String> onLogin(@RequestBody UserLoginRequestDto request){
        try{
            return userService.login(request).toResponse(HttpStatus.CREATED);
        }catch (AbstractMSHackException e){
            return e.toResult();
        }
    }

}
