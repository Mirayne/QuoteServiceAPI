package kameleoon.trialtask.quoteserviceapi.controllers;

import kameleoon.trialtask.quoteserviceapi.database.UsersTable;
import kameleoon.trialtask.quoteserviceapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value="", produces = "application/json")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/user/create")
    public UsersTable createUser(@RequestParam(value = "username", required = true) String username,
                                 @RequestParam(value = "email", required = true) String email,
                                 @RequestParam(value = "password", required = true) String password) {
        return userService.createUser(username, email, password);
    }

    @GetMapping("/user/get/{id}")
    public UsersTable getUser(@PathVariable Long id) {
        return userService.findById(id);
    }
}
