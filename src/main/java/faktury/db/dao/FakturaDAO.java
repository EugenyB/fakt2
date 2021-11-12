package faktury.db.dao;

import faktury.db.entities.Artykul;
import faktury.db.entities.Client;
import faktury.db.DBConnection;
import faktury.db.entities.Faktura;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Performs all DB operations with Faktura Table and creates list of Artykuls
 * Gets connection from DBConnection object (Singleton)
 */
public class FakturaDAO {

    /**
     * Finds all fakturas for client
     * @param client client for find it's fakturas
     * @return list of all fakturas for client
     */
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

    /**
     * Gets artykul by id from ArtykulDAO, as done with another object - it's private
     * @param idArtykul id of artykul
     * @return artykul object that created from DB with ArtykulDAO
     */
    private Artykul findArtykul(int idArtykul) {
        return new ArtykulDAO().find(idArtykul);
    }

    /**
     * Adds new Faktura line to DB
     * @param number quantity of goods
     * @param nipClient NIP of client
     * @param idArtykul id of goods
     */
    public void addFaktura(int number, long nipClient, int idArtykul) {
        Connection connection = DBConnection.getInstance().getConnection();
        try (PreparedStatement statement = connection.prepareStatement("insert into faktura (number, id_artykul, nip_client) VALUES (?,?,?)")) {
            statement.setInt(1, number);
            statement.setInt(2, idArtykul);
            statement.setLong(3, nipClient);
            statement.executeUpdate();
        } catch (SQLException ignored) { }
    }

    /**
     * Deletes line from faktura table
     * @param item record for delete
     */
    public void delete(Faktura item) {
        Connection connection = DBConnection.getInstance().getConnection();
        try (PreparedStatement statement = connection.prepareStatement("delete from faktura where id = ?")) {
            statement.setInt(1, item.getId());
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
