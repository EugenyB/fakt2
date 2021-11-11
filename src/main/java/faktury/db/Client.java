package faktury.db;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Client {
    private long nip;
    private String name;

    @Override
    public String toString() {
        return name + "(" + nip + ")";
    }
}
