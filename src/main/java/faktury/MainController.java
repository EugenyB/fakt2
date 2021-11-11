package faktury;

import faktury.db.Artykul;
import faktury.db.Client;
import faktury.db.Faktura;
import faktury.db.dao.ArtykulDAO;
import faktury.db.dao.ClientDAO;
import faktury.db.dao.FakturaDAO;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;
import java.util.stream.Collectors;

public class MainController {

    @FXML
    private TableView<Faktura> fTable;
    @FXML
    private TableColumn<Faktura, String> artykulColumn;
    @FXML
    private TableColumn<Faktura, Double> cenaColumn;
    @FXML
    private TableColumn<Faktura, Integer> numberColumn;
    @FXML
    private TextField numberField;
    @FXML
    private ListView<Artykul> artykulsListView;
    @FXML
    private ListView<Client> clientListView;

    public void initialize() {
        artykulColumn.setCellValueFactory(new PropertyValueFactory<>("artykulStr"));
        cenaColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        numberColumn.setCellValueFactory(new PropertyValueFactory<>("number"));
        fillClients();
        fillArtykuls();
        clientListView.getSelectionModel().selectedItemProperty().addListener((o,oldV,newV)->{
            Client client = clientListView.getSelectionModel().getSelectedItem();
            if (client != null) {
                showFakturaList(client);
            }
        });
    }

    private void fillArtykuls() {
        artykulsListView.setItems(FXCollections.observableList(new ArtykulDAO().findAll()));
    }

    private void fillClients() {
        List<Client> clients = new ClientDAO().findAll();
        clientListView.setItems(FXCollections.observableList(clients));
    }

    public void showFaktura() {
        Client client = clientListView.getSelectionModel().getSelectedItem();
        if (client!=null) {
            Alert alert = new Alert(Alert.AlertType.NONE);
            alert.setTitle("Faktura - " + client.getName() + " - " + client.getNip());
            alert.setHeaderText(null);
            String contentText = createFakturaText(client);
            alert.setContentText(contentText);
            alert.getButtonTypes().add(ButtonType.CLOSE);
            alert.show();
        }
    }

    private String createFakturaText(Client client) {
        if (client == null) return null;
        List<Faktura> fakturas = new FakturaDAO().findByClient(client);
        String s = fakturas.stream().map(Faktura::getFakturaString).collect(Collectors.joining("\n"));
        double sum = fakturas.stream().mapToDouble(f -> f.getNumber() * f.getPrice()).sum();
        return s + "\n------------------------\nSum = " + sum;
    }

    public void addArtykulToClientFaktura() {
        Client client = clientListView.getSelectionModel().getSelectedItem();
        Artykul artykul = artykulsListView.getSelectionModel().getSelectedItem();
        if (client==null || artykul == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Select Client and artykul first!");
            alert.show();
            return;
        }

        int number;
        try {
            number = Integer.parseInt(numberField.getText());
            if (number <= 0) throw new IllegalArgumentException();
        } catch (IllegalArgumentException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText(null);
            alert.setContentText("Number must be positive int");
            alert.show();
            return;
        }
        long nipClient = client.getNip();
        int idArtykul = artykul.getId();
        new FakturaDAO().addFaktura(number, nipClient, idArtykul);
        numberField.clear();
        showFakturaList(client);
    }

    private void showFakturaList(Client client) {
        if (client == null) return;
        List<Faktura> fakturas = new FakturaDAO().findByClient(client);
        fillFakturaTable(fakturas);
    }

    private void fillFakturaTable(List<Faktura> fakturas) {
        fTable.setItems(FXCollections.observableList(fakturas));
    }
}