package com.ebanking.service;

import com.ebanking.dao.UserDAO;
import com.ebanking.model.User;

public class AuthService {
    private final UserDAO userDAO = new UserDAO();

    public User login(String username, String password) {
        if (username == null || username.trim().isEmpty()) return null;
        if (password == null || password.trim().isEmpty()) return null;
        return userDAO.login(username, password);
    }
}
