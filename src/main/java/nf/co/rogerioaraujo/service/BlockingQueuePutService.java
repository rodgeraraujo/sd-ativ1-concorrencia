package nf.co.rogerioaraujo.service;

import nf.co.rogerioaraujo.dao.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class BlockingQueuePutService {
    private static Long tempoInicial;

    private static Integer id = new Integer(1);
    private static final Integer MAX = 1000;

    private static final String sqlInserr = "INSERT INTO Usuario(id, Nome) VALUES (?,?);";
    private static final String sqlAtualizar = "UPDATE Usuario SET updated = TRUE WHERE id = ?";
    private static final String sqlDeletar = "UPDATE Usuario SET deleted = TRUE WHERE id = ?";


    private Connection connection;
    private Database db = new Database();

    public BlockingQueuePutService() throws SQLException {
        this.connection = db.getConnection();
    }

    public void blockingQueuePut() throws InterruptedException {
        BlockingQueue<Integer> queueInserir = new ArrayBlockingQueue<>(10);
        BlockingQueue<Integer> queueAtualizar = new ArrayBlockingQueue<>(10);
        BlockingQueue<Integer> queueDeletar = new ArrayBlockingQueue<>( 10);

        Runnable inserir = () -> {
            try {
                int localId = Integer.valueOf(queueInserir.take());
                PreparedStatement stmtInserir = connection.prepareStatement(sqlInserr);

                stmtInserir.setInt(1, localId);
                stmtInserir.setString(2, String.format("Nome%d - ", localId));
                stmtInserir.executeUpdate();

                queueAtualizar.put(localId);

            } catch (Exception ex) {
                ex.printStackTrace();
                return;
            }
        };

        Runnable atualizar = () -> {
            try {
                Integer localId = new Integer(queueAtualizar.take());
                PreparedStatement stmtUpdate = connection.prepareStatement(sqlAtualizar);

                stmtUpdate.setInt(1, localId);
                stmtUpdate.executeUpdate();

                queueDeletar.put(localId);
            } catch (Exception ex) {
                ex.printStackTrace();
                return;
            }
        };

        Runnable deletar = () -> {
            try {
                Integer localId = new Integer(queueDeletar.take());
                PreparedStatement stmtDelete = connection.prepareStatement(sqlDeletar);

                stmtDelete.setInt(1, localId);
                stmtDelete.executeUpdate();

                if (localId.equals(MAX)) {
                    Long tempoFinal = System.currentTimeMillis();
                    System.out.printf("\n\n" + (tempoFinal - tempoInicial) + "ms\n\n");
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                return;
            }
        };

        tempoInicial = System.currentTimeMillis();

        while (MAX >= id) {

            queueInserir.put(id++);

            new Thread(inserir).start();
            new Thread(atualizar).start();
            new Thread(deletar).start();

        }
    }


}
