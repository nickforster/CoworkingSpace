package ch.zli.m223.ksh20.user.service;

import ch.zli.m223.ksh20.user.model.User;
import ch.zli.m223.ksh20.user.repository.UserRepository;
import ch.zli.m223.ksh20.user.service.exception.FailedToAuthorizeException;
import ch.zli.m223.ksh20.user.service.exception.InvalidParameterException;
import ch.zli.m223.ksh20.user.service.exception.UserAlreadyExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> getUserList() {
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
}
