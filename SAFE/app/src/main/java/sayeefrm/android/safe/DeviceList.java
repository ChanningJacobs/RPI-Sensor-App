package sayeefrm.android.safe;

import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Random;

public class DeviceList extends AppCompatActivity {

    //device[] = firebase_user.get_owned_devices

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private DatabaseReference mUserDB;
    private DatabaseReference mDevice;
    private String UserID;
    private Integer device_count;

   // Device list
    private ArrayList<String> deviceNames = new ArrayList<>();
    private ArrayList<String> deviceHashes = new ArrayList<>();
    private ArrayList<Integer> deviceImages = new ArrayList<>();

    // Set adapter for recycler
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_list);

        // Query for devices owned by the user
        UserID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        mUserDB = FirebaseDatabase.getInstance().getReference().child("users").child(UserID);
        mUserDB.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                device_count = (int)dataSnapshot.getChildrenCount();
                Log.d("SNAPSHOT", dataSnapshot.child("email").getValue().toString());
//                for(DataSnapshot devices: dataSnapshot.getChildren()){
//                    Log.d("SNAPSHOT", devices.child("email").toString());
//                }
                // Create devices

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        // Generate list of devices
        Device[] devices = new Device[1];
        for (int i = 0; i < devices.length; i++) {
            Log.d("CREATE_DEVICE", "" + i);
            devices[i] = new Device("Device: " + i, "testhash");
            deviceNames.add(devices[i].name);
            deviceHashes.add(devices[i].hash);
            deviceImages.add(generateImageColor());
            Log.d("CREATE_DEVICE", devices[i].name);
            Log.d("CREATE_DEVICE", devices[i].hash);
        }

        // Find recycler view
        mRecyclerView = (RecyclerView) findViewById(R.id.device_recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        //mRecyclerView.setHasFixedSize(true);

        // Set default layout manager to linear
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        // Divider drawn in device item layout instead of implementing this
        // DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(mRecyclerView.getContext(), DividerItemDecoration.HORIZONTAL);
        // mRecyclerView.addItemDecoration(dividerItemDecoration);

        mAdapter = new DeviceViewAdapter(this, deviceNames, deviceImages, deviceHashes);
        mRecyclerView.setAdapter(mAdapter);

    }

    private Integer generateImageColor() {
        Integer[] colors = new Integer[11];
        colors[0] = R.color.safe_red;
        colors[1] = R.color.safe_orange;
        colors[2] = R.color.safe_yellow;
        colors[3] = R.color.safe_green;
        colors[4] = R.color.safe_blue;
        colors[5] = R.color.safe_purple;
        colors[6] = R.color.safe_pink;
        colors[7] = R.color.safe_aqua;
        colors[8] = R.color.safe_tan;
        colors[9] = R.color.safe_brown;
        colors[10] = R.color.safe_black;
        return colors[new Random().nextInt(11)];
    }
}
