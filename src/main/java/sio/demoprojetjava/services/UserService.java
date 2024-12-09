package sio.demoprojetjava.services;

import sio.demoprojetjava.model.User;
import sio.demoprojetjava.repositories.UserRepository;

import java.sql.SQLException;
import java.util.ArrayList;

public class UserService {
    private UserRepository userRepository;

    public UserService() {
        this.userRepository = new UserRepository();
    }

    public ArrayList<User> getAll() throws SQLException {
        return userRepository.getAll();
    }

    public void deleteUser(int idUser) throws SQLException {
        userRepository.deleteUser(idUser);
    }

    public Boolean checkCredentials(String email, String enteredPassword) throws SQLException {
        return userRepository.checkCredentials(email, enteredPassword);
    }
}
