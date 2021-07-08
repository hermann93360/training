package com.java.training.controller;

import com.java.training.dto.UserDto;
import com.java.training.model.User;
import com.java.training.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity<UserDto> addUser(@RequestBody UserDto newUser) throws  Exception{
        UserDto userDto = userService.newUser(newUser);

        if(userDto == null){
            throw new Exception();
        }

        return ResponseEntity.ok(userDto);
    }
}
