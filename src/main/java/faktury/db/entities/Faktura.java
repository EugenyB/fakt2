package faktury.db.entities;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Faktura {
    private int id;
    private int number;
    private Artykul artykul;
    private Client client;

    /**
     * artykul String property for view
     * @return title of artykul
     */
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
