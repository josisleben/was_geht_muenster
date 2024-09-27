package ms.wasgeht.user.service;

import ms.wasgeht.user.dto.UserLoginRequestDto;
import ms.wasgeht.user.dto.UserLoginResponseDto;
import ms.wasgeht.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public UserLoginResponseDto login(UserLoginRequestDto request){
        return  null;
    }

    private String generateSessionKey(){
        return  null;
    }
}
