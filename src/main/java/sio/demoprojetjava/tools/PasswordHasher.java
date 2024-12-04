package sio.demoprojetjava.tools;
import org.springframework.security.crypto.bcrypt.BCrypt;

public class PasswordHasher {

    public boolean verifyPassword(String hash, String password) {
        return BCrypt.checkpw(password, hash);
    }
}
