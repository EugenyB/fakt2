package faktury.db.dao;

import faktury.db.entities.Artykul;
import faktury.db.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Performs all DB operations with Artykul Table and creates Artykul Objects by id and All list of Artykuls
 * Gets connection from DBConnection object (Singleton)
 */
public class ArtykulDAO {
    /**
     * Gets all artykuls from DB
     * @return list of all artykuls
     */
    public List<Artykul> findAll() {
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("select * from artykul");
            List<Artykul> artykuls = new ArrayList<>();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String title = resultSet.getString("title");
                double price = resultSet.getDouble("price");
                artykuls.add(new Artykul(id, title, price));
            }
            return artykuls;
        } catch (SQLException e) {
            return Collections.emptyList();
        }
    }

    /**
     * Gets artykul by it's id
     * @param id id artykul for find
     * @return found artykul
     */
    public Artykul find(int id) {
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            try (PreparedStatement statement = connection.prepareStatement("select * from artykul where id = ?")) {
                statement.setInt(1, id);
                ResultSet resultSet = statement.executeQuery();
                if (resultSet.next()) {
                    return new Artykul(id, resultSet.getString("title"), resultSet.getDouble("price"));
                } else return null;
            }
        } catch (SQLException e) {
            return null;
        }
    }
}
