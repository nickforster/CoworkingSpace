package ch.zli.m223.ksh20.user.repository;

import java.util.Optional;

import ch.zli.m223.ksh20.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    default User insertUser(User user) {
        return save(user);
    }

    public Optional<User> findByEmail(String email);

    default User authorize(String email, String password) {
        Optional<User> u = findByEmail(email);
        if (u.isPresent() && u.get().getPassword().equals(password)) return u.get();
        return null;
    }

    // Optional<User> updateUserById(User user);
}
