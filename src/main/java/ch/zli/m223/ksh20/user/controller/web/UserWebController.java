/* package ch.zli.m223.ksh20.user.controller.web;

import ch.zli.m223.ksh20.user.model.AppUser;
import ch.zli.m223.ksh20.user.service.UserServicewsdf;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class UserWebController {
    @Autowired
    private UserServicewsdf userService;

    @GetMapping("/web/users")
    String getUserList(Model model) {
        List<AppUser> users = userService.getUserList();

        //Add data to model
        model.addAttribute("users", users);

        //Pocess with 'userList' template
        return "userList";
    }

    @GetMapping("/web/users/{id}")
    String getUserById(Model model, @PathVariable Long id) {
        AppUser user = userService.getUserById(id);

        //Add data to model
        model.addAttribute("user", user);

        //Pocess with 'userList' template
        return "showUser";
    }

    @GetMapping("/error")
    String error() {
        return "error";
    }

    @PostMapping("/web/users")
    String addUser(Model model, @RequestParam String firstname, @RequestParam String surname, @RequestParam String email, @RequestParam String password) {
        AppUser user = userService.addUser(firstname, surname, email, password);
        if (user != null) {
            List<AppUser> users = userService.getUserList();
            model.addAttribute("users", users);
            return "userList";
        } else {
            return "error";
        }
    }

}
*/
