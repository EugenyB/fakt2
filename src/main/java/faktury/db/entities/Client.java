package faktury.db.entities;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Object that maps on Client table in DB
 */
@Data
@AllArgsConstructor
public class Client {
    /**
     * NIP of client
     */
    private long nip;

    /**
     * name of client
     */
    private String name;

    @Override
    public String toString() {
        return name + "(" + nip + ")";
    }
}
