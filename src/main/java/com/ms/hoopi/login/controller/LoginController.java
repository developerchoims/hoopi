package com.ms.hoopi.login.controller;

import com.ms.hoopi.join.UsersDto;
import com.ms.hoopi.login.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/hoopi")
public class LoginController {

    @Autowired
    private final LoginService loginService;

    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UsersDto users) {
        System.out.println(users);
        if(users != null){
            if(loginService.validateUser(users)){
                return ResponseEntity.status(HttpStatus.CREATED).body("Login successful");
            }
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Invalid username or password");
    }
}
