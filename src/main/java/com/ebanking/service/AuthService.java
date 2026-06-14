package com.ebanking.service;

import com.ebanking.dao.UserDAO;
import com.ebanking.model.User;

public class AuthService {
    private final UserDAO userDAO = new UserDAO();

    public User login(String username, String password) {
        return userDAO.login(username, password);
    }
    
    public User register (String name, String phone, String email, String username, String password) {
        return userDAO.register(name, phone, email, username, password);
    }
}
