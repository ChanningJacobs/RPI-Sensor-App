package sayeefrm.android.safe;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignInActivity extends AppCompatActivity {

    private EditText mEmailText;
    private EditText mPasswordText;
    private Button mSignInButton;
    private Button mRegisterButton;
    private FirebaseAuth mAuth;
    private DatabaseReference mUserDB;

    private final String USER_DB = "users";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        mEmailText = findViewById(R.id.user_email);
        mPasswordText = findViewById(R.id.user_password);
        mSignInButton = findViewById(R.id.sign_in_button);
        mRegisterButton = findViewById(R.id.register_button);
        mAuth = FirebaseAuth.getInstance();
        mUserDB = FirebaseDatabase.getInstance().getReference().child(USER_DB);
        Log.d("SAFE-APP", "able to get reference to users");
    }

    @Override
    public void onStart() {
        super.onStart();

        mRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = mEmailText.getText().toString();
                final String password = mPasswordText.getText().toString();
                Log.d("SAFE-APP", "entered the on click for the register button");
                if(validatePassword(password)) {
                    mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(SignInActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            Log.d("SAFE-APP", "entered on complete on registration");
                            if (task.isSuccessful()) {
                                FirebaseUser user = mAuth.getCurrentUser();
                                Log.d("SAFE-APP", "current user: " + user.getEmail());
                                User newUser = new User(user.getUid(), email, password);
                                mUserDB.child(user.getUid()).setValue(newUser);
                                launchMainActivity();
                            }
                        }
                    });
                } else {
                    // show something to the user that their email or password arent good
                }
            }
        });


        mSignInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = mEmailText.getText().toString();
                final String password = mPasswordText.getText().toString();
                mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(SignInActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            // launch an intent to go to the main activity
                            launchMainActivity();
                        } else {
                            Toast.makeText(SignInActivity.this, "Sign in failed",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });



    }

    private boolean validatePassword(String password) {
        return true;
    }

    private void launchMainActivity() {
        Intent mainActivity = new Intent(SignInActivity.this, MainActivity.class);
        startActivity(mainActivity);
    }


}