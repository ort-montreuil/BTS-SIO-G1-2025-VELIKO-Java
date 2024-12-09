package sio.demoprojetjava.controller;

import sio.demoprojetjava.model.User;
import sio.demoprojetjava.services.UserService;

import java.sql.SQLException;
import java.util.ArrayList;

public class UserController {
    private UserService userService;

    public UserController() {
        this.userService = new UserService();
    }
    public ArrayList<User> getAll() throws SQLException {
        return userService.getAll();
    }
    public void deleteUser(int idUser) throws SQLException {
        userService.deleteUser(idUser);
    }

    public Boolean checkCredentials(String email, String enteredPassword) throws SQLException {
        return userService.checkCredentials(email, enteredPassword);
    }
}
