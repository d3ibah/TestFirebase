package by.testfirebase;

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

public class MainActivity extends AppCompatActivity {

    private EditText editTextMail, editTextPass;
    private Button buttonSignIn, buttonReg;

    private static final String TAG = "EmailPassword";

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextMail = findViewById(R.id.editTextEmail);
        editTextPass = findViewById(R.id.editTextPass);
        buttonSignIn = findViewById(R.id.buttonSignIn);
        buttonReg = findViewById(R.id.buttonReg);

        mAuth = FirebaseAuth.getInstance();

        FirebaseAuth.AuthStateListener authStateListener;
    }

    @Override
    protected void onStart() {
        super.onStart();

        buttonSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn(editTextMail.getText().toString(), editTextPass.getText().toString());
            }
        });

        buttonReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this, RegistrationFormActivity.class);
                intent.putExtra("regMail", editTextMail.getText().toString());
                intent.putExtra("regPass", editTextPass.getText().toString());

                startActivity(intent);
//                createAccount(editTextMail.getText().toString(), editTextPass.getText().toString());
            }
        });


        FirebaseUser currentUser = mAuth.getCurrentUser();
    }



    private void signIn(String email, String password) {
        Log.d(TAG, "signIn:" + email);

        // [START sign_in_with_email]
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            Log.e(TAG, user.getEmail());
                            Toast.makeText(MainActivity.this, "Sign In is successful." + user.getEmail(),
                                    Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(MainActivity.this, AddAndShowActivity.class);
                            startActivity(intent);

                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(MainActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        // [END sign_in_with_email]
    }
}
