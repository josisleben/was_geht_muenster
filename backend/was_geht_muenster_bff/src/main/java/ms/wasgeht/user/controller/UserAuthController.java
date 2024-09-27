package ms.wasgeht.user.controller;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import ms.wasgeht.exceptions.AbstractMSHackException;
import ms.wasgeht.exceptions.result.ErrorResult;
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
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "Created", content =
                    { @Content(mediaType = "application/json", schema =
                    @Schema(implementation = UserLoginResponseDto.class)) }),
            @ApiResponse(description = "On Error",content =
                    { @Content(mediaType = "application/json", schema =
                    @Schema(implementation = ErrorResult.class)) })
            })
    public ResponseEntity<String> onLogin(@RequestBody UserLoginRequestDto request){
        try{
            return userService.login(request).toResponse(HttpStatus.CREATED);
        }catch (AbstractMSHackException e){
            return e.toResult();
        }
    }

}
