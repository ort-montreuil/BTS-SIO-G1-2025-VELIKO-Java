package sio.demoprojetjava.controller;

import sio.demoprojetjava.services.UserService;

import java.sql.SQLException;

public class UserController {
    private UserService userService;

    public UserController() {
        this.userService = new UserService();
    }

    public Boolean checkCredentials(String email, String enteredPassword) throws SQLException {
        return userService.checkCredentials(email, enteredPassword);
    }
}
