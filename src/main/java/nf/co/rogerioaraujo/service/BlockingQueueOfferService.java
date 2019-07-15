package nf.co.rogerioaraujo.service;

import nf.co.rogerioaraujo.dao.Database;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class BlockingQueueOfferService {
    private static Long tempoInicial;

    private static Integer id = new Integer(1);
    private static final Integer MAX = 1000;

    private static final String sqlInserir = "INSERT INTO Usuario(id, Nome) VALUES (?,?);";
    private static final String sqlAtualizar = "UPDATE Usuario SET updated = TRUE WHERE id = ?";
    private static final String sqlDeletar = "UPDATE Usuario SET deleted = TRUE WHERE id = ?";


    private Connection connection;
    private Database db = new Database();

    public BlockingQueueOfferService() throws SQLException {
        this.connection = db.getConnection();
    }

    public void blockingQueueOffer() {
        BlockingQueue<Integer> queueInserir = new ArrayBlockingQueue<>(3);
        BlockingQueue<Integer> queueAtualizar = new ArrayBlockingQueue<>(3);
        BlockingQueue<Integer> queueDeletar = new ArrayBlockingQueue<>(3);

        Runnable inserir = () -> {
            try {
                int localId = Integer.valueOf(queueInserir.take());
                PreparedStatement stmtInsert = connection.prepareStatement(sqlInserir);

                stmtInsert.setInt(1, localId);
                stmtInsert.setString(2, String.format("Nome%d", localId));
                stmtInsert.executeUpdate();

                while (!queueAtualizar.offer(localId));

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

                while (!queueDeletar.offer(localId));
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

//                System.out.println(localId);
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

            queueInserir.offer(id++);

            new Thread(inserir).start();
            new Thread(atualizar).start();
            new Thread(deletar).start();

        }
    }


}
