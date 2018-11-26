package sayeefrm.android.safe;

import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import java.util.ArrayList;

public class DeviceList extends AppCompatActivity {

    //device[] = firebase_user.get_owned_devices

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

   // Device list
    private ArrayList<String> deviceNames = new ArrayList<>();
    private ArrayList<String> deviceHashes = new ArrayList<>();
    private ArrayList<Integer> deviceImages = new ArrayList<>();

    // Set adapter for recycler
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_list);

        deviceNames.add("Test Name 1");
        deviceNames.add("Test Name 2");
        deviceNames.add("Test Name 3");
        deviceNames.add("Test Name 4");
        deviceNames.add("Test Name 5");
        deviceNames.add("Test Name 6");
        deviceNames.add("Test Name 7");
        deviceNames.add("Test Name 8");
        deviceNames.add("Test Name 9");
        deviceNames.add("Test Name 10");
        deviceNames.add("Test Name 11");

        deviceHashes.add("H@5H Test 1");
        deviceHashes.add("H@5H Test 2");
        deviceHashes.add("H@5H Test 3");
        deviceHashes.add("H@5H Test 4");
        deviceHashes.add("H@5H Test 5");
        deviceHashes.add("H@5H Test 6");
        deviceHashes.add("H@5H Test 7");
        deviceHashes.add("H@5H Test 8");
        deviceHashes.add("H@5H Test 9");
        deviceHashes.add("H@5H Test 10");
        deviceHashes.add("H@5H Test 11");

        deviceImages.add(R.color.safe_red);
        deviceImages.add(R.color.safe_orange);
        deviceImages.add(R.color.safe_yellow);
        deviceImages.add(R.color.safe_green);
        deviceImages.add(R.color.safe_blue);
        deviceImages.add(R.color.safe_purple);
        deviceImages.add(R.color.safe_pink);
        deviceImages.add(R.color.safe_aqua);
        deviceImages.add(R.color.safe_tan);
        deviceImages.add(R.color.safe_brown);
        deviceImages.add(R.color.safe_black);


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
                // TODO: handle adding a device here
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void generateImageColors() {
        return;
    }
}
