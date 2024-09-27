package ms.wasgeht.session.controller;

// SessionController.java
import ms.wasgeht.activity.category.ActivityCategoryType;
import ms.wasgeht.exceptions.AbstractMSHackException;
import ms.wasgeht.session.SessionModel;
import ms.wasgeht.session.dto.SessionRequestDto;
import ms.wasgeht.session.service.SessionService;
import ms.wasgeht.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/sessions")
public class SessionController {

    @Autowired
    private SessionService sessionService;
    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<?> getSessions(@RequestParam(required = false) final ActivityCategoryType category, @RequestParam(defaultValue = "1") final int page, @RequestParam(defaultValue = "10") final int size) {
        return ResponseEntity.ok(sessionService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getSessionById(@PathVariable UUID id) {
        try{
            return this.sessionService.getSession(id).toResponse(HttpStatus.OK);
        }catch (AbstractMSHackException e){
            return e.toResult();
        }
    }

    @PostMapping
    public ResponseEntity<?> createSession(final @RequestHeader(name = "Authorization") String sessionToken, @RequestBody SessionRequestDto requestDto) {
        try{
            this.sessionService.createSession(this.userService.getUserBySessionToken(sessionToken), requestDto);
            return new ResponseEntity<>("Created", HttpStatus.CREATED);
        }catch (AbstractMSHackException e){
            return e.toResult();
        }
    }

}


