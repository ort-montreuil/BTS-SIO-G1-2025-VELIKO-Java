package sio.demoprojetjava.tools;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.TimeZone;

public class DataSourceProvider {
    private static Connection cnx;

    // Méthode pour initialiser ou récupérer une connexion valide
    public static Connection getCnx() {
        if (cnx == null || isClosed(cnx)) {
            try {
                String pilote = "com.mysql.cj.jdbc.Driver";
                Class.forName(pilote); // Charge le driver MySQL
                cnx = DriverManager.getConnection(
                        "jdbc:mysql://localhost/app_db?serverTimezone=" + TimeZone.getDefault().getID(),
                        "root",
                        "root"
                );
                System.out.println("Connexion à la base de données établie.");
            } catch (ClassNotFoundException e) {
                System.err.println("Pilote MySQL introuvable : " + e.getMessage());
            } catch (SQLException e) {
                System.err.println("Erreur lors de la connexion à la base de données : " + e.getMessage());
            }
        }
        return cnx;
    }

    // Méthode pour vérifier si une connexion est fermée
    private static boolean isClosed(Connection connection) {
        try {
            return connection == null || connection.isClosed();
        } catch (SQLException e) {
            return true; // Considérer comme fermée en cas d'erreur
        }
    }
}
