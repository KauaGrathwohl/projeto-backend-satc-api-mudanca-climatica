package com.satc.projeto.mudancaclimatica.controllers;

import com.satc.projeto.mudancaclimatica.dto.UserDto;
import com.satc.projeto.mudancaclimatica.models.User;
import com.satc.projeto.mudancaclimatica.repository.UserRepository;
import com.satc.projeto.mudancaclimatica.services.AuthenticationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private UserRepository userRepository;

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

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteUser(@PathVariable String id) {
        userRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}