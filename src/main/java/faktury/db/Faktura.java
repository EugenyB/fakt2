package faktury.db;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Faktura {
    private int id;
    private int number;
    private Artykul artykul;
    private Client client;

    public String getArtykulStr() {
        return artykul.getTitle();
    }

    public double getPrice() {
        return artykul.getPrice();
    }

    public String getFakturaString() {
        return artykul.getTitle() + " " + artykul.getPrice() + " " + number;
    }
}
