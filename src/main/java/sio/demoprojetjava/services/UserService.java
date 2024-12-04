package sio.demoprojetjava.services;

import sio.demoprojetjava.repositories.UserRepository;

import java.sql.SQLException;

public class UserService {
    private UserRepository userRepository;

    public UserService() {
        this.userRepository = new UserRepository();
    }

    public Boolean checkCredentials(String email, String enteredPassword) throws SQLException {
        return userRepository.checkCredentials(email, enteredPassword);
    }
}
