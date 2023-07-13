package ch.zli.m223.ksh20.user.controller.rest;

import ch.zli.m223.ksh20.user.controller.rest.dto.UserDto;
import ch.zli.m223.ksh20.user.model.User;
import ch.zli.m223.ksh20.user.model.enums.Role;
import ch.zli.m223.ksh20.user.security.JwtResponse;
import ch.zli.m223.ksh20.user.security.JwtUtils;
import ch.zli.m223.ksh20.user.service.UserService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
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

    boolean firstUser = true;

    @PostMapping("/register")
    @ApiOperation("Register a new user")
    User register(@RequestBody @ApiParam("User data") UserDto u) {
        User user = new User(u.getFirstname(), u.getLastname(), u.getEmail(), u.getPassword());
        if (firstUser) {
            user.setRole(Role.ADMIN);
            firstUser = false;
        }
        return userService.addUser(user);
    }

    @PostMapping("/login")
    @ApiOperation("Login as member or admin")
    ResponseEntity<?> login(@RequestBody @ApiParam("User data") UserDto u) {
        User user = userService.authorize(u.getEmail(), u.getPassword());
        String token = jwtUtils.generateJwtToken(user.getEmail(), user.getRole(), user.getId());

        return ResponseEntity.ok(new JwtResponse(token));
    }

    @GetMapping("/list")
    @ApiOperation("List all users")
    List<UserDto> getUserList(
            @RequestHeader("Authorization") @ApiParam("Authorization header with jwt token") String header
    ) {
        String token = header.split(" ")[1].trim();

        return userService.getUserList(token).stream().map(UserDto::new).collect(Collectors.toList());
    }

    @DeleteMapping("/{id}/delete")
    @ApiOperation("Delete a user")
    void deleteUser(
            @PathVariable @ApiParam("Id of the user") Long id,
            @RequestHeader("Authorization") @ApiParam("Authorization header with jwt token") String header
    ) {
        String token = header.split(" ")[1].trim();

        userService.deleteUser(id, token);
    }

    @PutMapping("/{id}/update")
    @ApiOperation("Update a user")
    User updateUser(
            @PathVariable @ApiParam("Id of the user") Long id,
            @RequestBody @ApiParam("User data") UserDto userDto,
            @RequestHeader("Authorization") @ApiParam("Authorization header with jwt token") String header
    ) {
        String token = header.split(" ")[1].trim();

        return userService.updateUser(id, userDto, token);
    }
}
