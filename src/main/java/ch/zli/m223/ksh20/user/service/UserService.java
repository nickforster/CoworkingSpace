package ch.zli.m223.ksh20.user.service;

import ch.zli.m223.ksh20.user.controller.rest.dto.UserDto;
import ch.zli.m223.ksh20.user.model.User;
import ch.zli.m223.ksh20.user.model.enums.Role;
import ch.zli.m223.ksh20.user.repository.UserRepository;
import ch.zli.m223.ksh20.user.security.JwtUtils;
import ch.zli.m223.ksh20.user.service.exception.FailedToAuthorizeException;
import ch.zli.m223.ksh20.user.service.exception.InvalidParameterException;
import ch.zli.m223.ksh20.user.service.exception.UnauthorizedException;
import ch.zli.m223.ksh20.user.service.exception.UserAlreadyExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtils jwtUtils;


    public List<User> getUserList() {
        return userRepository.findAll();
    }

    public List<User> getUserList(String token) {
        if (!jwtUtils.validateJwtToken(token)) throw new UnauthorizedException();
        if (Role.valueOf(jwtUtils.getRoleFromJwtToken(token)) != Role.ADMIN) throw new UnauthorizedException();

        return userRepository.findAll();
    }

    public User getUserById(Long id) {
        return userRepository.getReferenceById(id);
    }

    public User addUser(User user) {
        if (user.getEmail() == null ||
                user.getEmail().isBlank() ||
                user.getPassword() == null ||
                user.getPassword().isBlank()
        ) {
            throw new InvalidParameterException();
        }

        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new UserAlreadyExistsException();
        }

        return userRepository.insertUser(user);
    }

    public User authorize(String email, String password) {
        Optional<User> u = userRepository.findByEmail(email);
        if (u.isPresent() && u.get().getPassword().equals(password)) {
            return u.get();
        }
        throw new FailedToAuthorizeException();
    }

    public void deleteUser(Long id, String token) {
        if (!jwtUtils.validateJwtToken(token)) throw new UnauthorizedException();
        if (Objects.equals(jwtUtils.getIdFromJwtToken(token), id)) {
            userRepository.deleteById(id);
            return;
        }
        if (Role.valueOf(jwtUtils.getRoleFromJwtToken(token)) != Role.ADMIN) throw new UnauthorizedException();

        userRepository.deleteById(id);
    }

    public User updateUser(Long id, UserDto userDto, String token) {
        if (!jwtUtils.validateJwtToken(token)) throw new UnauthorizedException();
        if (Role.valueOf(jwtUtils.getRoleFromJwtToken(token)) == Role.GUEST) throw new UnauthorizedException();


        User u = new User(userDto.getFirstname(), userDto.getLastname(), userDto.getEmail(), userDto.getPassword());
        u.setRole(userDto.getRole());
        u.setId(id);

        if (Objects.equals(jwtUtils.getIdFromJwtToken(token), id)
                || Role.valueOf(jwtUtils.getRoleFromJwtToken(token)) == Role.ADMIN
        ) {
            return userRepository.save(u);
        } else {
            throw new UnauthorizedException();
        }
    }
}
