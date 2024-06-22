package com.satc.projeto.mudancaclimatica.controllers;

import com.satc.projeto.mudancaclimatica.dto.UserDto;
import com.satc.projeto.mudancaclimatica.models.User;
import com.satc.projeto.mudancaclimatica.services.AuthenticationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private AuthenticationService authenticationService;

    @PostMapping("signup")
    public ResponseEntity<UserDto> signup(@Valid @RequestBody User user) {
        UserDto userDto = authenticationService.signup(user);
        return ResponseEntity.ok(userDto);
    }

    @PostMapping("signin")
    public ResponseEntity<String> signin(@RequestBody User user) {
        String token = authenticationService.signin(user);
        return ResponseEntity.ok(token);
    }
}