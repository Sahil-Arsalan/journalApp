package com.alamara.journalApp.controller;

import com.alamara.journalApp.Entity.User;
import com.alamara.journalApp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/public")
public class PublicController {

    @Autowired
    private UserService userService;

    @GetMapping("/health-check")
    public String healthCheck()
    {
        return "ok";
    }


    @PostMapping("/create-user")
    public void addUser(@RequestBody User user)
    {
        userService.saveNewUser(user);
    }
}
