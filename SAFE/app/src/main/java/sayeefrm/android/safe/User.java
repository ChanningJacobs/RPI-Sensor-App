package sayeefrm.android.safe;

import java.util.HashMap;
import java.util.Map;

public class User {
    String id;
    String email;
    String password;
    Map<String, Boolean> devices;

    public User(String id, String email, String password) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.devices = new HashMap<>();
    }

}
