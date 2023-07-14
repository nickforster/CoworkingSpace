package ch.zli.m223.ksh20.user.init;

import ch.zli.m223.ksh20.user.model.User;
import ch.zli.m223.ksh20.user.model.enums.Role;
import ch.zli.m223.ksh20.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class ServerInit implements ApplicationRunner {
    @Autowired
    private UserRepository userRepository;

    @Override
    public void run(ApplicationArguments args) {
        User u = new User("admin", "admin", "admin", "admin");
        u.setRole(Role.ADMIN);
        userRepository.insertUser(u);
        userRepository.insertUser(new User("Max", "SI-UU", "max@siu.ch", "asd34454"));
        userRepository.insertUser(new User("OmegaMax", "tax", "dajfha@gmail.com", "dhjfaadf"));

    }
}
