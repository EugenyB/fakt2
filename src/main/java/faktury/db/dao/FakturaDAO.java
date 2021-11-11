package faktury.db.dao;

import faktury.db.Artykul;
import faktury.db.Client;
import faktury.db.DBConnection;
import faktury.db.Faktura;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FakturaDAO {
    public List<Faktura> findByClient(Client client) {
        try {
            Connection connection = DBConnection.getInstance().getConnection();
            try (PreparedStatement statement = connection.prepareStatement("select * from faktura where nip_client = ?")) {
                statement.setLong(1, client.getNip());
                ResultSet resultSet = statement.executeQuery();
                List<Faktura> fakturas = new ArrayList<>();
                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    int number = resultSet.getInt("number");
                    Artykul a = findArtykul(resultSet.getInt("id_artykul"));
                    fakturas.add(new Faktura(id, number, a, client));
                }
                return fakturas;
            }
        } catch (SQLException e) {
            return Collections.emptyList();
        }
    }

    private Artykul findArtykul(int idArtykul) {
        return new ArtykulDAO().find(idArtykul);
    }

    public void addFaktura(int number, long nipClient, int idArtykul) {
        Connection connection = DBConnection.getInstance().getConnection();
        try (PreparedStatement statement = connection.prepareStatement("insert into faktura (number, id_artykul, nip_client) VALUES (?,?,?)")) {
            statement.setInt(1, number);
            statement.setInt(2, idArtykul);
            statement.setLong(3, nipClient);
            statement.executeUpdate();
        } catch (SQLException ignored) { }
    }
}
