package sayeefrm.android.safe;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.Set;

public class Device {
     String enabled;
     String hash;
     String name;
//    private ArrayList<String> owner_hashes;

    Device(String enabled, String hash, String name){
        this.enabled = enabled;
        this.hash = hash;
        this.name = name;
    }

//    public void addOwner(DatabaseReference ref, FirebaseUser user){
//        // Maybe use getIdToken instead?
//        owner_hashes.add(user.getUid());
//        return;
//    }
//
//    public void removeOwner(DatabaseReference ref, FirebaseUser user){
//        // Maybe use getIdToken instead?
//        owner_hashes.remove(user.getUid());
//        return;
//    }
//
//    public ArrayList<String> listOwners(){
//        return owner_hashes;
//    }
//
//    // Change name?
}
