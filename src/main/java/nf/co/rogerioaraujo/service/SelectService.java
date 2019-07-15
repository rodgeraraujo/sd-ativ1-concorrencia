package nf.co.rogerioaraujo.service;

import nf.co.rogerioaraujo.dao.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SelectService {
    private Connection connection;
    private Database db = new Database();

    public SelectService() throws SQLException {
        this.connection = db.getConnection();
    }

    public void buscar() {


        try {
            PreparedStatement statement = null;

            statement = connection.prepareStatement("SELECT * FROM TABLE_1");

            long tempoInicial = System.currentTimeMillis();

            ResultSet resultSet = statement.executeQuery();

            long tempoFinal = System.currentTimeMillis();

            System.out.println(tempoFinal - tempoInicial + "ms");

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
