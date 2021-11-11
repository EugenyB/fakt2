package faktury.db.dao;

import faktury.db.Artykul;
import faktury.db.Client;
import faktury.db.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ArtykulDAO {
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
