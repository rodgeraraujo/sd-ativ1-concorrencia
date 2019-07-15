package nf.co.rogerioaraujo.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBManager {

    private Connection connection = null;

    private static final String BANCO = "ativ1";
    private static final String HOST = "localhost:";
    private static final String PORTA = "5432";
    private static final String URL = "jdbc:postgresql://" + HOST + PORTA;
    private static final String USUARIO = "postgres";
    private static final String SENHA = "dockerpass";


    public Connection getConnection() {
        try {
            return connection = DriverManager.getConnection(URL, USUARIO, SENHA);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return connection;

    }
}
