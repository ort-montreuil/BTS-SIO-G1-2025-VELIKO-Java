package sio.demoprojetjava.model;

public class User {

    private int idUser;
    private String emailUser;
    private String passwordUser;

    public User(int idUser, String emailUser, String passwordUser) {
        this.idUser = idUser;
        this.emailUser = emailUser;
        this.passwordUser = passwordUser;
    }
    public User(String emailUser, String passwordUser) {
        this.emailUser = emailUser;
        this.passwordUser = passwordUser;
    }

    public int getIdUser() {
        return idUser;
    }

    public String getEmailUser() {
        return emailUser;
    }

    public String getPasswordUser() {
        return passwordUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public void setEmailUser(String emailUser) {
        this.emailUser = emailUser;
    }

    public void setPasswordUser(String passwordUser) {
        this.passwordUser = passwordUser;
    }
}
