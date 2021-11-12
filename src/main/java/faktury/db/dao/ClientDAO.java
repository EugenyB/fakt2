package faktury.db.dao;

import faktury.db.entities.Client;
import faktury.db.DBConnection;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Performs all DB operations with Client Table and creates list of all clients
 * Gets connection from DBConnection object (Singleton)
 */
public class ClientDAO {
    /**
     * Gets list of all clients from DB
     * @return list of clients
     */
    public List<Client> findAll() {
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from client");
            List<Client> clients = new ArrayList<>();
            while (resultSet.next()) {
                long nip = resultSet.getLong("nip");
                String name = resultSet.getString("name");
                clients.add(new Client(nip, name));
            }
            return clients;
        } catch (SQLException e) {
            return Collections.emptyList();
        }
    }
}
