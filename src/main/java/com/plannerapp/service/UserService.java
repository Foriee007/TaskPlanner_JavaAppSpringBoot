package com.plannerapp.service;

import com.plannerapp.model.dto.UserRegisterDto;
import com.plannerapp.model.entity.User;

import java.util.Optional;

public interface UserService {
    Optional<User> findUserByUsername(String username);
    Optional<User> findUserByEmail(String email);
    boolean checkCredentials(String username, String password);
    void login(String username);

    boolean register(UserRegisterDto registerDTO);

    void logout();

}
