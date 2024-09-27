package ms.wasgeht.session.controller;

// SessionController.java
import ms.wasgeht.session.SessionModel;
import ms.wasgeht.session.service.SessionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/sessions")
public class SessionController {

    @Autowired
    private SessionService sessionService;

    @GetMapping
    public ResponseEntity<?> getSessions() {
        return ResponseEntity.ok(sessionService.findAll());
    }

    @PostMapping
    public ResponseEntity<?> createSession(@RequestBody SessionModel sessionModel) {
        return ResponseEntity.ok(sessionService.save(sessionModel));
    }

}


