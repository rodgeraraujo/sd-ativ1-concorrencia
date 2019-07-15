package nf.co.rogerioaraujo.service;

import nf.co.rogerioaraujo.dao.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.concurrent.atomic.AtomicInteger;

public class SimpleService {

    private static final AtomicInteger id = new AtomicInteger(0);
    private static final Integer MAX = 100;

    private static final String sqlInserir = "INSERT INTO Usuario(id, Nome) VALUES (?,?);";
    private static final String sqlAtualizar = "UPDATE Usuario SET updated = TRUE";
    private static final String sqlDeletar = "UPDATE Usuario SET deleted = TRUE";

    private Connection connection;
    private Database db = new Database();

    public SimpleService() throws SQLException {
        this.connection = db.getConnection();
    }


    public void inserirAtualizarDeletar() {

        try {
            connection = Database.getConnection();

            PreparedStatement inserir = connection.prepareStatement(sqlInserir);
            PreparedStatement atualizar = connection.prepareStatement(sqlAtualizar);
            PreparedStatement deletar = connection.prepareStatement(sqlDeletar);

            Long tempoInicial = System.currentTimeMillis();

            while (MAX > id.get()) {

                inserir.setInt(1, id.get());
                inserir.setString(2, String.format("Usuario - %d", id.getAndIncrement()));
                inserir.executeUpdate();

                atualizar.executeUpdate();

                deletar.executeUpdate();
            }

            Long tempoFinal = System.currentTimeMillis();

            System.out.println(tempoFinal - tempoInicial + "ms");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
