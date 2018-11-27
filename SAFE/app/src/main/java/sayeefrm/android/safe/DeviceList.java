package sayeefrm.android.safe;

import android.content.Intent;
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

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

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
    private Device[] device_arr;

   // Device list
    private ArrayList<String> deviceNames = new ArrayList<>();
    private ArrayList<String> deviceHashes = new ArrayList<>();
    private ArrayList<Integer> deviceImages = new ArrayList<>();

    // Set adapter for recycler
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_list);

        /* Query for devices owned by the user
            !!!!!
        This is not a full implementation. The database is only accessed on updates to data.
        Adding one device will work. Fix this to allow for more devices without having to add
        a new device.
            !!!!!
         */
        UserID = FirebaseAuth.getInstance().getCurrentUser().getUid();
        mUserDB = FirebaseDatabase.getInstance().getReference().child("users").child(UserID).child("owned");

        mUserDB.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                device_count = (int)dataSnapshot.getChildrenCount();
                Log.d("COUNT", device_count.toString());
                device_arr = new Device[device_count];
                //Log.d("SNAPSHOT", dataSnapshot.child("email").getValue().toString());
                int i = 0;
                for(DataSnapshot device : dataSnapshot.getChildren()){
                    device_arr[i] = new Device(device.child("enabled").getValue().toString(), device.child("hash").getValue().toString(), device.child("title").getValue().toString());
                    Log.d("CHILD_DEV", device.child("enabled").getValue().toString());
                    Log.d("CHILD_DEV", device.child("hash").getValue().toString());
                    Log.d("CHILD_DEV", device.child("title").getValue().toString());
                    i++;
                }
                // Generate list of devices (move this later?...see above comment warning)
                if (device_arr != null) {
                    for (int j = 0; j < device_arr.length; j++) {
                        Log.d("CREATE_DEVICE", "" + j);
                        deviceNames.add(device_arr[j].name);
                        deviceHashes.add(device_arr[j].hash);
                        deviceImages.add(generateImageColor());
                        Log.d("CREATE_DEVICE", device_arr[j].enabled);
                        Log.d("CREATE_DEVICE", device_arr[j].hash);
                        Log.d("CREATE_DEVICE", device_arr[j].name);
                    }
                }
                mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


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

    public void onResume() {
        super.onResume();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.add_device_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_add_device:
                Intent scan_intent = new Intent(this, NFCScanActivity.class);
                startActivity(scan_intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
