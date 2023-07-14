package ch.zli.m223.ksh20.user.repository;

import java.util.Optional;

import ch.zli.m223.ksh20.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    default User insertUser(User user) {
        return save(user);
    }

    Optional<User> findByEmail(String email);
}
