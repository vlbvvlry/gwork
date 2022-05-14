package com.iem.iemserver.controllers;

import com.iem.iemserver.models.User;
import com.iem.iemserver.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    @Autowired
    UserRepository userRepository;

    /* 101 means user not exist.
    *  102 means user exist but password is incorrect.
    *  100 means something wrong. Error is unknown.
    *  200 ??(id) ??(level) means login and password is correct. Access allowed.
    *  It's a test way for authentication. There will be use Spring Security in the future.
    * */
    @PostMapping("/login")
    public @ResponseBody String login(@RequestParam String login,
                                      @RequestParam String password) {
        User user = userRepository.findByLogin(login);
        if(user == null) {
            return "101";
        }
        if(!user.getPassword().equals(password)) {
            return "102";
        }
        String response = "200";
        response = String.join(" ", response, Integer.toString(user.getId()));
        response = String.join(" ", response, Integer.toString(user.getLevel()));
        return response;
    }

}
