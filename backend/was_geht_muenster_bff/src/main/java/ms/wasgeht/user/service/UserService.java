package ms.wasgeht.user.service;

import ms.wasgeht.exceptions.AbstractMSHackException;
import ms.wasgeht.exceptions.MissingFieldException;
import ms.wasgeht.exceptions.user.UserNotFoundException;
import ms.wasgeht.exceptions.user.UsernameAlreadyExistsException;
import ms.wasgeht.user.UserModel;
import ms.wasgeht.user.dto.UserLoginRequestDto;
import ms.wasgeht.user.dto.UserLoginResponseDto;
import ms.wasgeht.user.repository.UserRepository;
import ms.wasgeht.util.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.UUID;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;


    public boolean exists(final UUID uuid){
        return this.userRepository.existsById(uuid);
    }

    public boolean existsByUsername(final String username){
        return this.userRepository.existsByUsernameEqualsIgnoreCase(username);
    }

    public void save(final UserModel userModel){
        this.userRepository.save(userModel);
    }

    public UserModel getUserBySessionToken(final String sessionToken) throws UserNotFoundException {
        return this.userRepository.findBySessionTokenEquals(sessionToken).orElseThrow(UserNotFoundException::new);
    }

    public UserLoginResponseDto login(final UserLoginRequestDto request) throws AbstractMSHackException {
        if(request.getUsername() == null || request.getUsername().isEmpty() || request.getUsername().isBlank())
            throw new MissingFieldException("Username");
        if(request.getBirthDate() == null)
            throw new MissingFieldException("BirthDate");
        if(this.existsByUsername(request.getUsername()))
            throw new UsernameAlreadyExistsException();

        final UserModel userModel = UserModel.builder()
                .id(this.generateFreeId())
                .username(request.getUsername())
                .birthDate(request.getBirthDate())
                .sessionToken(this.generateSessionToken())
                .avatar(Base64.getDecoder()
                        .decode(request.getAvatar())).build();
        this.save(userModel);
        return UserLoginResponseDto.builder().sessionToken(userModel.getSessionToken()).build();
    }

    private String generateSessionToken(){
        String token;
        do{
            token = new RandomString(128, new SecureRandom(), RandomString.alphanum).nextString();
        }while (this.userRepository.existsBySessionTokenEquals(token));
        return token;
    }

    private UUID generateFreeId(){
        UUID id;
        do{
            id = UUID.randomUUID();
        }while(this.exists(id));
        return id;
    }

}
