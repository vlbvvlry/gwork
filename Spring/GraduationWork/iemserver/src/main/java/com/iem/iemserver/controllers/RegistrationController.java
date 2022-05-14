package com.iem.iemserver.controllers;

import com.iem.iemserver.models.User;
import com.iem.iemserver.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RegistrationController {

    @Autowired
    UserRepository userRepository;

    @PostMapping("/registration")
    public @ResponseBody void registration(@RequestParam String fullName,
                                           @RequestParam String login,
                                           @RequestParam String password,
                                           @RequestParam short level) {
        User user = new User();
        user.setFullName(fullName);
        user.setLogin(login);
        user.setPassword(password);
        user.setLevel(level);
        userRepository.save(user);
    }
}
