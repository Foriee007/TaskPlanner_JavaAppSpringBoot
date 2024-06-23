package com.plannerapp.service.impl;

import com.plannerapp.model.dto.UserRegisterDto;
import com.plannerapp.config.UserSession;
import com.plannerapp.model.entity.User;
import com.plannerapp.repo.UserRepository;
import com.plannerapp.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserSession userSession;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, UserSession userSession, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userSession = userSession;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Optional<User> findUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public Optional<User> findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public boolean checkCredentials(String username, String password) {
        Optional<User> user = userRepository.findByUsername(username);
        return user.isPresent() && passwordEncoder.matches(password, user.get().getPassword());
    }

    @Override
    public void login(String username) {
        Optional<User> user = userRepository.findByUsername(username);
        user.ifPresent(value -> userSession.login(value.getId(), value.getUsername()));
    }

    @Override
    public boolean register(UserRegisterDto registerDTO) {
        if (userRepository.findByUsername(registerDTO.getUsername()).isPresent() || userRepository.findByEmail(registerDTO.getEmail()).isPresent()) {
            return false;
        }
        User user = new User();
        user.setUsername(registerDTO.getUsername());
        user.setEmail(registerDTO.getEmail());
        user.setPassword(passwordEncoder.encode(registerDTO.getPassword()));
        userRepository.save(user);
        return true;
    }

    @Override
    public void logout() {
        userSession.logout();
    }
}
