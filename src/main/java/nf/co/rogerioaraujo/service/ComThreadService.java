package nf.co.rogerioaraujo.service;

import nf.co.rogerioaraujo.dao.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.concurrent.atomic.AtomicInteger;

public class ComThreadService {
    private static final AtomicInteger id = new AtomicInteger(0);
    private static final Integer MAX = 100;

    private static final String sqlInserir = "INSERT INTO Usuario(id, Nome) VALUES (?,?);";
    private static final String sqlAtualizar = "UPDATE Usuario SET updated = TRUE WHERE id = ?";
    private static final String sqlDeletar = "UPDATE Usuario SET deleted = TRUE WHERE id = ?";

    private Connection connection;
    private Database db = new Database();

    public ComThreadService() throws SQLException {
        this.connection = db.getConnection();
    }

    public void comThreads() {
        PreparedStatement stmtInserir = null;
        try {
            stmtInserir = connection.prepareStatement(sqlInserir);

            PreparedStatement stmtAtualizar = connection.prepareStatement(sqlAtualizar);
            PreparedStatement stmtDeletar = connection.prepareStatement(sqlDeletar);

            PreparedStatement finalStmtInserir = stmtInserir;

            Runnable inserir = () -> {
                try {
                    finalStmtInserir.setInt(1, id.getAndIncrement());
                    finalStmtInserir.setString(2, String.format("Nome%d", id.getAndIncrement()));
                    finalStmtInserir.executeUpdate();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    return;
                }
            };

            Runnable atualizar = () -> {
                try {
                    stmtAtualizar.setInt(1, id.getAndIncrement());
                    stmtAtualizar.executeUpdate();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    return;
                }
            };

            Runnable deletar = () -> {
                try {
                    stmtDeletar.setInt(1, id.getAndIncrement());
                    stmtDeletar.executeUpdate();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    return;
                }
            };

            Long tempoInicial = System.currentTimeMillis();

            int count = 0;
            while (MAX > count) {
                new Thread(inserir).start();
                new Thread(atualizar).start();
                new Thread(deletar).start();
                count++;
            }

            Long tempoFinal = System.currentTimeMillis();


            System.out.println(tempoFinal - tempoInicial + "ms");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
