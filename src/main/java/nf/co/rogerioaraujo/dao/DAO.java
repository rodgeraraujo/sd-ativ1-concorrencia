package nf.co.rogerioaraujo.dao;

import nf.co.rogerioaraujo.database.DBManager;
import nf.co.rogerioaraujo.model.Key;
import nf.co.rogerioaraujo.model.Usuario;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DAO {

    private DBManager dbManager;
    private Connection connection;
    private Object lock = new Object();
    private Key key;
    private int max;


    public DAO(int max) {
        this.connection = dbManager.getConnection();
        this.lock = lock;
        this.key = key;
        this.max = max;
    }

    public void salvar(Usuario usuario) {
        String sql = "INSERT INTO Usuario (nome, atualizado, deletado) VALUES (?,?,?,?)";

        try {
            PreparedStatement statement = connection.prepareStatement(sql);

            statement.setString(1,usuario.getNome());
            statement.setBoolean(2,false);
            statement.setBoolean(3,false);
            statement.executeUpdate();

            statement.close();
            connection.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void atualizar(long id) {
        String sql = "UPDATE Usuario SET atualizado=? WHERE id=?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setBoolean(1, true);
            statement.setLong(2, id);
            statement.executeUpdate();

            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleta(long id) {
        String sql = "UPDATE Usuario SET deletado=? WHERE id=?";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setBoolean(1, true);
            statement.setLong(2, id);
            statement.executeUpdate();

            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void inserirKey() {
        synchronized (lock) {
            Key key;

            try {
              key = new Key();

            } catch (Exception e) {
                key = null;
            }

            if (key == null) {
                key = new Key();
                key.setId(0);
            } else {
                key.setId((key.getId() + 1));

            }

            String sql = "INSERT INTO Chave (codigoEntidade) VALUE (?)";
            try {
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setLong(1, key.getCodigoEntidade());
            } catch (SQLException e) {
                e.printStackTrace();
            }


        }
    }

    public Key getKeys() {
        String sql = "SELECT * FORM Chave ORDER BY id";
        Key chave = new Key();
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();

            while (rs.next()) {
                chave.setId(rs.getLong(1));
                chave.setCodigoEntidade(rs.getLong(2));

            }


        } catch (SQLException e) {
            e.printStackTrace();
        }


        return chave;
    }
}
