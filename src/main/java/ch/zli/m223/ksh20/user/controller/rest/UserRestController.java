package ch.zli.m223.ksh20.user.controller.rest;

import ch.zli.m223.ksh20.user.controller.rest.dto.UserDto;
import ch.zli.m223.ksh20.user.model.User;
import ch.zli.m223.ksh20.user.security.JwtResponse;
import ch.zli.m223.ksh20.user.security.JwtUtils;
import ch.zli.m223.ksh20.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/user")
public class UserRestController {

    @Autowired
    private UserService userService;

    @Autowired
    private JwtUtils jwtUtils;

    @PostMapping("/register")
    User register(@RequestBody UserDto u) {
        return userService.addUser(new User(u.getFirstname(), u.getLastname(), u.getEmail(), u.getPassword()));
    }

    @PostMapping("/login")
    ResponseEntity<?> login(@RequestBody UserDto u) {
        User user = userService.authorize(u.getEmail(), u.getPassword());
        String token = jwtUtils.generateJwtToken(user.getEmail(), user.getRole(), user.getId());

        return ResponseEntity.ok(new JwtResponse(token));
    }

    @GetMapping("/list")
    List<UserDto> getUserList(
            @RequestHeader("Authorization") String header
    ) {
        String token = header.split(" ")[1].trim();

        return userService.getUserList(token).stream().map(UserDto::new).collect(Collectors.toList());
    }

    @DeleteMapping("/{id}/delete")
    void deleteUser(
            @PathVariable Long id,
            @RequestHeader("Authorization") String header
    ) {
        String token = header.split(" ")[1].trim();

        userService.deleteUser(id, token);
    }

    @PutMapping("/{id}/update")
    User updateUser(
            @PathVariable Long id,
            @RequestBody UserDto userDto,
            @RequestHeader("Authorization") String header
    ) {
        String token = header.split(" ")[1].trim();

        return userService.updateUser(id, userDto, token);
    }
}
