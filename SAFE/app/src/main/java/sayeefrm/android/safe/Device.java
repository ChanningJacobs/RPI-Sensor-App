package sayeefrm.android.safe;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.Set;

public class Device {
    private String name;
    private String hash;
    private ArrayList<String> owner_hashes;

    Device(String name, String hash){
        this.name = name;
        this.hash = hash;
    }

    public void addOwner(DatabaseReference ref, FirebaseUser user){
        // Maybe use getIdToken instead?
        owner_hashes.add(user.getUid());
        return;
    }

    public void removeOwner(DatabaseReference ref, FirebaseUser user){
        // Maybe use getIdToken instead?
        owner_hashes.remove(user.getUid());
        return;
    }

    public ArrayList<String> listOwners(){
        return owner_hashes;
    }

    // Change name?
}
