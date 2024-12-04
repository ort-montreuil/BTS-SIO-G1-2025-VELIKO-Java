package sio.demoprojetjava.repositories;

import javafx.scene.control.Alert;
import sio.demoprojetjava.model.User;
import sio.demoprojetjava.tools.DataSourceProvider;
import sio.demoprojetjava.tools.PasswordHasher;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRepository {
    private Connection cnx;
    private PreparedStatement ps;
    private ResultSet rs;

    public UserRepository()
    {
        this.cnx = DataSourceProvider.getCnx();
    }


    public Boolean checkCredentials(String email, String enteredPassword) throws SQLException {
        PreparedStatement ps = cnx.prepareStatement("SELECT email, password FROM user WHERE roles = '[\"ROLE_ADMIN\"]' AND email = ?");
        ps.setString(1, email);
        boolean result = false;
        ResultSet rs = ps.executeQuery();
        PasswordHasher passwordHasher = new PasswordHasher();
        while (rs.next()) {
            if (passwordHasher.verifyPassword(rs.getString("password"), enteredPassword) && rs.getString("email").equals(email)) {
                result = true;
            }
        }
        ps.close();
        rs.close();
        return result;
    }
}
