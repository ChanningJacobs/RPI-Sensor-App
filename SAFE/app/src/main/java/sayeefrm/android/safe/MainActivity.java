package sayeefrm.android.safe;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class MainActivity extends AppCompatActivity {

    TextView mDistanceLabel;
    TextView mDistanceMeasurement;
    TextView mTimeLabel;
    TextView mTimeStamp;
    DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDistanceLabel = (TextView) findViewById(R.id.distance_label);
        mDistanceMeasurement = (TextView) findViewById(R.id.distance_measurement);
        mTimeLabel = (TextView) findViewById(R.id.time_label);
        mTimeStamp = (TextView) findViewById(R.id.time_measurement);
        mDatabase = FirebaseDatabase.getInstance().getReference();

        DatabaseReference dbRef = mDatabase.child("measurements");

        dbRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                String distance = (String)dataSnapshot.child("distance").getValue();
                String time = (String)dataSnapshot.child("time").getValue();
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
