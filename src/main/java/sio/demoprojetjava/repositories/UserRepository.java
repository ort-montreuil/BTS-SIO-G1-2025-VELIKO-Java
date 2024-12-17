package sio.demoprojetjava.repositories;

import javafx.scene.control.Alert;
import sio.demoprojetjava.model.User;
import sio.demoprojetjava.tools.DataSourceProvider;
import sio.demoprojetjava.tools.PasswordHasher;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class UserRepository implements RepositoryInterface<User, Integer> {
    private Connection cnx;
    private PreparedStatement ps;
    private ResultSet rs;

    public UserRepository() {
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


    private void showAlert(String title, String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.setHeaderText(null);
        alert.showAndWait();
    }


    @Override
    public void create(User obj) throws SQLException {
    }

    @Override
    public void update(User obj) throws SQLException {

        }


    @Override
    public void delete(User obj) throws SQLException {
    }

    @Override
    public User getById(int id) {
        return null;
    }

    @Override
    public ArrayList<User> getAll() throws SQLException {
        PreparedStatement ps = cnx.prepareStatement("SELECT id,nom,prenom,email,is_verified,is_bloqued,is_forced_mdp FROM user WHERE roles = '[]'");
        ResultSet rs = ps.executeQuery();
        ArrayList<User> users = new ArrayList<>();
        while (rs.next()) {
            users.add(new User(
                    rs.getInt("id"),
                    rs.getString("nom"),
                    rs.getString("prenom"),
                    rs.getString("email"),
                    //rs.getString("password"),
                    rs.getInt("is_verified"),
                    rs.getBoolean("is_bloqued"),
                    rs.getBoolean("is_forced_mdp")
            ));
        }
        ps.close();
        rs.close();
        return users;
    }
    public void deleteUser(int idUser) throws SQLException {
        String query = "UPDATE user SET nom='anonyme@Veliko', prenom='anonyme', email='anonyme', adresse='anonyme' WHERE id = ?";

        try (PreparedStatement ps = cnx.prepareStatement(query)) {
            ps.setInt(1, idUser);  // Assurez-vous que obj.getIdUser() donne l'ID de l'utilisateur
            ps.executeUpdate();
        }
    }
    public boolean isBlocker(User user) throws SQLException {
        PreparedStatement preparedStatement = cnx.prepareStatement("SELECT is_bloqued FROM user WHERE user.id=?");
        preparedStatement.setInt(1, user.getIdUser());
        ResultSet resultSet = preparedStatement.executeQuery();
        resultSet.next();
        return resultSet.getBoolean("is_bloqued");
    }

    public void blocker(Integer id) throws SQLException {
        ps = cnx.prepareStatement("UPDATE user SET is_bloqued = true WHERE id = ?");
        ps.setInt(1, id);
        ps.executeUpdate();
    }

    public void deblocker(Integer id) throws SQLException {
        ps = cnx.prepareStatement("UPDATE user SET is_bloqued = false WHERE id = ?");
        ps.setInt(1, id);
        ps.executeUpdate();
    }

    public void passwordForced(int idUser) throws SQLException {
        String query = "UPDATE user SET is_forced_mdp = true WHERE id = ?";

        try (PreparedStatement ps = cnx.prepareStatement(query)) {
            ps.setInt(1, idUser);
            ps.executeUpdate();
        }
    }


}