package ms.wasgeht.user.service;

import ms.wasgeht.WasGehtMuensterBffApplication;
import ms.wasgeht.exceptions.AbstractMSHackException;
import ms.wasgeht.exceptions.MissingFieldException;
import ms.wasgeht.exceptions.user.UserNotFoundException;
import ms.wasgeht.exceptions.user.UsernameAlreadyExistsException;
import ms.wasgeht.user.UserModel;
import ms.wasgeht.user.dto.UserLoginRequestDto;
import ms.wasgeht.user.dto.UserLoginResponseDto;
import ms.wasgeht.user.dto.UserResponseDto;
import ms.wasgeht.user.repository.UserRepository;
import ms.wasgeht.util.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.Optional;
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

    public UserModel getUserById(final UUID id) throws UserNotFoundException {
        return this.userRepository.findById(id).orElseThrow(UserNotFoundException::new);
    }

    public UserResponseDto getUser(final UserModel requestedBy, final UUID id) throws UserNotFoundException {
        UserResponseDto responseDto = this.getUser(id);
        if(requestedBy.getId().equals(id))
            responseDto.setBirthDate(requestedBy.getBirthDate());
        return responseDto;
    }

    public UserResponseDto getUser(final UUID id) throws UserNotFoundException {
        final UserModel userModel = this.getUserById(id);
        return UserResponseDto.builder().id(id).username(userModel.getUsername()).avatar("http://45.142.107.241:8080/api/v1/user/" + id + "/avatar").build();
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
        return UserLoginResponseDto.builder().id(userModel.getId())
                .sessionToken(userModel.getSessionToken()).build();
    }

    public byte[] getAvatar(UUID uuid){
        UserModel user;
        try{
            user = this.getUserById(uuid);
        }catch (UserNotFoundException e){
            return WasGehtMuensterBffApplication.getDefaultAvatar();
        }
        return user.getAvatar();
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
