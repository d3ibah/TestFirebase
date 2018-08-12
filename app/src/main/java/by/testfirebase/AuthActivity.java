package by.testfirebase;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AuthActivity extends AppCompatActivity {

    private EditText editTextMail, editTextPass;
    private Button buttonSignIn, buttonReg, buttonNavDr;
    ImageView ivVisiblePass;

    private FirebaseAuth mAuth;
    private FirebaseUser user;

    private boolean flagShowPass = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        editTextMail = findViewById(R.id.editTextEmail);
        editTextPass = findViewById(R.id.editTextPass);
        buttonSignIn = findViewById(R.id.buttonSignIn);
        buttonReg = findViewById(R.id.buttonReg);
        buttonNavDr = findViewById(R.id.buttonNavDr);
        ivVisiblePass = findViewById(R.id.ivShowHide);

        mAuth = FirebaseAuth.getInstance();

        user = mAuth.getCurrentUser();

        if (user != null) {
            startActivity(new Intent(this, Main2Activity.class));
            finish();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        buttonSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkEditFilds();
                if (checkEditFilds()) {

                    signIn(editTextMail.getText().toString(), editTextPass.getText().toString());
                }
            }
        });


        buttonReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(AuthActivity.this, RegistrationFormActivity.class);
                intent.putExtra("regMail", editTextMail.getText().toString());
                intent.putExtra("regPass", editTextPass.getText().toString());
                startActivity(intent);
            }
        });

        buttonNavDr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AuthActivity.this, Main2Activity.class);
                startActivity(intent);
            }
        });

        ivVisiblePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showOrHidePassword();
            }
        });
    }

    private void signIn(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            user = mAuth.getCurrentUser();
                            Toast.makeText(AuthActivity.this, "Sign In is successful." + user.getEmail(),
                                    Toast.LENGTH_SHORT).show();

                            Intent intent = new Intent(AuthActivity.this, Main2Activity.class);
                            startActivity(intent);
                            finish();

                        } else {
                            Toast.makeText(AuthActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private boolean checkEditFilds(){
        boolean isOk = false;

        String email= editTextMail.getText().toString();
        if (TextUtils.isEmpty(email)) {
            editTextMail.setError("Required.");
            isOk = false;
        } else {
            editTextMail.setError(null);
            isOk = true;
        }

        String password = editTextPass.getText().toString();
        if (TextUtils.isEmpty(password)) {
            editTextPass.setError("Required.");
            isOk = false;
        } else {
            editTextPass.setError(null);
            isOk = true;
        }
        return isOk;
    }

    public void showOrHidePassword() {
        if (!flagShowPass) {
            ivVisiblePass.setImageResource(R.drawable.ic_visibility_36dp);
            editTextPass.setTransformationMethod(null);
            editTextPass.setSelection(editTextPass.length());
            flagShowPass = !flagShowPass;
        } else {
            ivVisiblePass.setImageResource(R.drawable.ic_visibility_off_36dp);
            flagShowPass = !flagShowPass;
            editTextPass.setTransformationMethod(new PasswordTransformationMethod());
        }
    }
}