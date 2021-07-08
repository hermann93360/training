package com.java.training.controller;

import com.java.training.dao.UserDao;
import com.java.training.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class AuthController {

    @Autowired
    private UserDao userDao;

    @RequestMapping(value = "/salut", method = RequestMethod.GET)
    public String afficher(){
        return "userDao.save(newUser);";
    }

}
