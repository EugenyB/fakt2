package faktury.db.entities;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Object that maps on table Artykul
 * Contains all info for one artykul
 */
@Data
@AllArgsConstructor
public class Artykul {
    private int id;
    /**
     * Artykul's name
     */
    private String title;

    /**
     * Artykul's price
     */
    private double price;

    @Override
    public String toString() {
        return title + '(' + price + ')';
    }
}
