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

import java.util.Date;
import java.util.UUID;

@RestController
@CrossOrigin
@RequestMapping("/api/sessions")
public class SessionController {

    @Autowired
    private SessionService sessionService;
    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<?> getSessions(@RequestParam(required = false) final Long start, @RequestParam(required = false) final Long end, @RequestParam(required = false) final ActivityCategoryType category, @RequestParam(defaultValue = "1") final int page, @RequestParam(defaultValue = "10") final int size) {
        return this.sessionService.getSessions(start, end, category, page, size).toResponse(HttpStatus.OK);
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

    @PostMapping("/{id}")
    public ResponseEntity<?> joinSession(final @RequestHeader(name = "Authorization") String sessionToken, @PathVariable UUID id) {
        try{
            this.sessionService.joinSession(this.userService.getUserBySessionToken(sessionToken), id);
            return new ResponseEntity<>("OK", HttpStatus.OK);
        }catch (AbstractMSHackException e){
            return e.toResult();
        }
    }

}


