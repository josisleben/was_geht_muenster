package ms.wasgeht.user.controller;

import ms.wasgeht.user.dto.UserLoginRequestDto;
import ms.wasgeht.user.dto.UserLoginResponseDto;
import ms.wasgeht.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/v1/user/auth")
@CrossOrigin
public class UserAuthController {

    @Autowired
    private UserService userService;

    @PostMapping("")
    public UserLoginResponseDto onLogin(UserLoginRequestDto request){
        return userService.login(request);
    }

}
