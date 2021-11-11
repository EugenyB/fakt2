package faktury.db;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Artykul {
    private int id;
    private String title;
    private double price;

    @Override
    public String toString() {
        return title + '(' + price + ')';
    }
}
