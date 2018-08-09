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
import com.google.firebase.database.FirebaseDatabase;

import by.testfirebase.dataModel.Article;
import by.testfirebase.dataModel.User;

public class RegistrationFormActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private static final String TAG = "EmailPassword";
    private Button buttonReg;
    private EditText etMail, etPass, etName, etSurname, etAge;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_form);

        buttonReg = findViewById(R.id.buttonRegForm);
        etMail = findViewById(R.id.etMail);
        etPass = findViewById(R.id.etPass);
        etName = findViewById(R.id.etName);
        etSurname = findViewById(R.id.etSurname);
        etAge = findViewById(R.id.etAge);

        mAuth = FirebaseAuth.getInstance();

        //RTIjh2VEY2e9uVhEX0vu9UttsKp2
    }

    @Override
    protected void onStart() {
        super.onStart();

        etMail.setText(getIntent().getStringExtra("regMail"));
        etPass.setText(getIntent().getStringExtra("regPass"));

        buttonReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createAccount(etMail.getText().toString(), etPass.getText().toString());


            }
        });
    }

    private void createAccount(String email, String password) {
        Log.d(TAG, "createAccount:" + email);

        // [START create_user_with_email]
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            Log.e(TAG, user.getEmail());
                            Toast.makeText(RegistrationFormActivity.this, "Create user is successful." + user.getEmail(),
                                    Toast.LENGTH_SHORT).show();

                            FirebaseDatabase.getInstance().getReference().push()
                                    .setValue(new User(FirebaseAuth.getInstance().getCurrentUser().getUid(), FirebaseAuth.getInstance().getCurrentUser().getEmail(),etName.getText().toString(), etSurname.getText().toString(), etAge.getText().toString()));

                            Intent intent = new Intent(RegistrationFormActivity.this, AddAndShowActivity.class);
                            startActivity(intent);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(RegistrationFormActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        // [END create_user_with_email]
    }
}
