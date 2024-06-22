package com.satc.projeto.mudancaclimatica.services;

import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;
import com.satc.projeto.mudancaclimatica.models.Role;
import com.satc.projeto.mudancaclimatica.dto.UserDto;
import com.satc.projeto.mudancaclimatica.models.User;
import com.satc.projeto.mudancaclimatica.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class AuthenticationService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public String signin(User user) {
        Authentication usernamePassword = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());
        Authentication authentication = authenticationManager.authenticate(usernamePassword);
        User principalUser = (User) authentication.getPrincipal();
        return tokenService.generateToken(principalUser);
    }

    public UserDto signup(User user) {
        User existentUser = userRepository.findByUsername(user.getUsername());
        if (Objects.nonNull(existentUser))
            throw new RuntimeException("Username already exists");

        user.setRole(Role.ADMIN);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        User userSaved = userRepository.save(user);
        Mapper mapper = DozerBeanMapperBuilder.buildDefault();
        return mapper.map(userSaved, UserDto.class);
    }
}
