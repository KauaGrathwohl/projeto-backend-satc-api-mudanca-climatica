package com.satc.projeto.mudancaclimatica.controllers;

import com.satc.projeto.mudancaclimatica.Role;
import com.satc.projeto.mudancaclimatica.models.User;
import com.satc.projeto.mudancaclimatica.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("signup")
    public ResponseEntity<User> signup(@RequestBody User user) {
        user.setRole(Role.ADMIN);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User userSaved = userRepository.save(user);
        return ResponseEntity.ok(userSaved);
    }

//    @PostMapping("signup")
//    public ResponseEntity signup(@RequestBody User user) {
//        userRepository.save(user);
//        return ResponseEntity.ok().build();
//    }
}
