package sayeefrm.android.safe;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class ActiveDevice extends AppCompatActivity {

    TextView mDistanceLabel;
    TextView mDistanceMeasurement;
    TextView mTimeLabel;
    TextView mTimeStamp;
    ImageView mImageView;
    DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_active_device);

        // Intent from DeviceViewAdapter (clicking a device in the list)
        Intent intent = getIntent();
        String device_id = intent.getStringExtra(DeviceViewAdapter.EXTRA_MESSAGE);

        mDistanceLabel = (TextView) findViewById(R.id.distance_label);
        mDistanceMeasurement = (TextView) findViewById(R.id.distance_measurement);
        mTimeLabel = (TextView) findViewById(R.id.time_label);
        mTimeStamp = (TextView) findViewById(R.id.time_measurement);
        mImageView = (ImageView) findViewById(R.id.night_image);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        DatabaseReference dbRef = mDatabase.child("devices").child(device_id).child("measurements");

        dbRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                String distance = (String)dataSnapshot.child("distance").getValue();
                String time = (String)dataSnapshot.child("time").getValue();
                String b64_str = (String)dataSnapshot.child("image").getValue();
                byte[] decoded_str = Base64.decode(b64_str, Base64.DEFAULT);
                Bitmap decodedByte = BitmapFactory.decodeByteArray(decoded_str, 0, decoded_str.length);
                mImageView.setImageBitmap(decodedByte);
                mDistanceMeasurement.setText(distance);
                mTimeStamp.setText(time);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
