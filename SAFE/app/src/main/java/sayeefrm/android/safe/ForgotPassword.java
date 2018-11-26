package sayeefrm.android.safe;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPassword extends AppCompatActivity {

    private EditText userEmail;
    private Button resetPass;
    private FirebaseAuth firebaseAuth;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        userEmail = findViewById(R.id.forgot_password_email);
        resetPass = findViewById(R.id.reset_password_button);
        progressBar = findViewById(R.id.progress_bar);

        firebaseAuth = FirebaseAuth.getInstance();

        resetPass.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                try {
                    firebaseAuth.sendPasswordResetEmail(userEmail.getText().toString())
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            progressBar.setVisibility(View.GONE);
                            Log.d("SAFE-APP", "entered the on click for the password reset button");
                            if(task.isSuccessful()){
                                Toast.makeText(ForgotPassword.this,
                                        "Reset email sent", Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(ForgotPassword.this,
                                        task.getException().getMessage(), Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                } catch (Exception e) {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(ForgotPassword.this,
                            "Enter an email address", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
