package sayeefrm.android.safe;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.ByteArrayOutputStream;


public class MainActivity extends AppCompatActivity {

    TextView mDistanceLabel;
    TextView mDistanceMeasurement;
    TextView mTimeLabel;
    TextView mTimeStamp;
    ImageView mImageView;
    DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDistanceLabel = (TextView) findViewById(R.id.distance_label);
        mDistanceMeasurement = (TextView) findViewById(R.id.distance_measurement);
        mTimeLabel = (TextView) findViewById(R.id.time_label);
        mTimeStamp = (TextView) findViewById(R.id.time_measurement);
        mImageView = (ImageView) findViewById(R.id.night_image);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        DatabaseReference dbRef = mDatabase.child("measurements");

        dbRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                String distance = (String)dataSnapshot.child("distance").getValue();
                String time = (String)dataSnapshot.child("time").getValue();

                String b64_str = (String)dataSnapshot.child("image").getValue();
                //System.out.println("TESTING HERE CHANNING");
                //System.out.println(b64_str);
                //System.out.println((String)dataSnapshot.child("image").getValue());
                //String b64_str = "iVBORw0KGgoAAAANSUhEUgAAAEAAAABACAYAAACqaXHeAAAEYUlEQVRIDdWXAZbbMAhEndz/zG39N5lUTwYEspxk+3aKDMOAMM2mt+2+/d28P7c9QBS7H4c/4pEzJA8IVY0q/1n+/rS2QZRLYW3Gf694Ge7/rI+f4gGoPS6ns2crF8/oeXXkp14L+Ys2HgCNqkhROKSjaRE8v8Vd5Ht8AkSFGQIYFRRHNuJ7HM9vacFtYXESvngDJBANSBzZiKuGI4503mRv2+35W4DmrKKeP+KuuGBWI8uz+t198Qbo8pkicOGBXfi3/Nz3Ddh+EHXM5aJ4NZbRy3CoCw9wnkC8ARXBypvPcDOcSn8ONx7AbBPeG/H8TnOhm95ahGQ/GA9ADVPI1zhGPL7n7xWyvD5v4nndbwGGtbJxtCqa8CcGkNuACeElKZOXqtSOB0ADIKOY5UVa0pCNuIrBBXou2ngAiFXWEP4KUHOFTkLjtj3+N2BTaYTpYm3GtV5qZytUuI1mbgOahPC4clCTFwr7M4LjAdAIMJIPrizvkPh0tPkMEzxDBwO3xYGQc4wHQBMgp3eO1dfhgucUh9mPTwCvkOcfyi4gjGozLHCy1HgDaARQSJbzFbha3+g5/iaoCdOYzr2I/Fh4fVw+4opx/qMHx5IHjzBnbAvL18aT53gDKkWu4KIJkpeZod03pgy8bBoYxeGQ3/LwAfxCG5dvZGdyRppNPN4AEfuLyN/bEW8U7/V4nskhL4ncACQWNUOshXJk2xhn+WUtn2IX2ngArJ9AE5yxV6DX7p+vqLlrxt8DdsL2oTczrMuAAD2eQLwBXJ4i2BNFvjk1HgCX/+buF/QWD4AC1bf/y4YWfw+oXn5mYOQIM/UYOJBG0cYbgLBQFJ6iU6tN7J/b2KJzPAC9EdlFRV8yowtm6sIBL9HaIR6AtEaNile1o8avqtv0GX8PoIFRk43Y8uMbascboAZkKzdkeBbf81vcN/hu22MH7FKfbDY79CzPvuF+fSdwqTszWDjg0ka2Dw0g89bggNEAGBIY8Zz4+DMg04Qj/hvc8QC++Qa8mBaTvY4HwHpRaLLAt6c9fgf8xgvyYsDJCccboAKyJ4uV0t9UM/7fYKnjBLlyKbaywk+UtyjxBtCElTXrq+pV+RN9xQOoCOpt9bai0XOl1fvbZzig9RXO8QAqwnpbvS00c6BK6xBY54gHsK5OTalycbigVuHFzg3gRIFXpeyBWpXNy+o6vHXfA9Q0FjgFf9xRvI2155/E9X/lNiBTlzcHDws4e7DiWZ+nOenPfQ/IvAlxsMBriBjo45av51jP5AErlvCt2wAVi5pRTFY5ns3yvPyE/7Y9PgWOVIqzlthjdM6DFprZ7Aw3wwnq+RuAMA2TzBl7FhUduKp/tm6Q/3j/FAtI2xsaMcuP+jKTak5/A9B5QwOU+SRyvwUyHWpLZDM5X8CJN+DsZcgHumh7ls+zFa6nkfDHA7AEaAy0MT3LEuPc/hPiWX6deY6Q4cEBkU4Q8wfQirYXscQUxwI4lsUnwBkB7ohzMv4P6gbBaLmp4i4AAAAASUVORK5CYII=";
                //Log.d("B64", b64_str.toString());
                byte[] decoded_str = Base64.decode(b64_str, Base64.DEFAULT);
                //java.util.Base64.getDecoder().decode(b64_str);
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
