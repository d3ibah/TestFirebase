package by.testfirebase;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import by.testfirebase.dataModel.User;

public class RegistrationFormActivity extends BaseActivity {

    private static final String TAG = "EmailPassword";
    public static final String USERS_CHILD = "usersList";
    private Button buttonReg, buttonClear;
    private EditText etMail, etPass, etName, etSurname, etAge;
    private TextView tvGender;
    private String userMail, userPassword, userName, userSurname, userGender, userAge;
    private RadioButton radioButtonMale, radioButtonFemale;
    private RadioGroup radioGroup;

    private FirebaseAuth mAuth;
    private DatabaseReference databaseReference;

    private String userUId;
    private String errorMessage;

    public static final String MALE = "male";
    public static final String FEMALE = "female";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_form);

        buttonReg = findViewById(R.id.buttonRegForm);
        buttonClear = findViewById(R.id.buttonClear);
        etMail = findViewById(R.id.etMail);
        etPass = findViewById(R.id.etPass);
        etName = findViewById(R.id.etName);
        etSurname = findViewById(R.id.etSurname);
        radioButtonMale = findViewById(R.id.radioButtonMale);
        radioButtonFemale = findViewById(R.id.radioButtonFemale);
        radioGroup = findViewById(R.id.radioGroup);
        etAge = findViewById(R.id.etAge);
        tvGender = findViewById(R.id.tvGender);

        errorMessage = (getResources().getText(R.string.field_is_empty)).toString();

        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    protected void onStart() {
        super.onStart();

        etMail.setText(getIntent().getStringExtra("regMail"));
        etPass.setText(getIntent().getStringExtra("regPass"));


        View.OnClickListener radioButtonClckListnr = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RadioButton rb = (RadioButton) view;
                switch (rb.getId()) {
                    case R.id.radioButtonMale:
                        userGender = MALE;
                        tvGender.setError(null);
                        break;
                    case R.id.radioButtonFemale:
                        userGender = FEMALE;
                        tvGender.setError(null);
                        break;
                }
            }
        };

        radioButtonMale.setOnClickListener(radioButtonClckListnr);
        radioButtonFemale.setOnClickListener(radioButtonClckListnr);

        buttonReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!checkAndAcceptFromFieldsInfo()) {
                    showDialogInfo();
                    return;
                }
                buttonReg.setEnabled(false);
                createAccount(userMail, userPassword, userName, userSurname, userGender, userAge);
            }
        });

        buttonClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clearAllFields();
            }
        });
    }

    private boolean checkAndAcceptFromFieldsInfo() {
        boolean isOk = true;
        userMail = etMail.getText().toString();
        if (TextUtils.isEmpty(userMail)) {
            etMail.setError(errorMessage);
            isOk = false;
        } else {
            etMail.setError(null);
        }

        userPassword = etPass.getText().toString();
        if (TextUtils.isEmpty(userPassword)) {
            etPass.setError(errorMessage);
            isOk = false;
        } else {
            etPass.setError(null);
        }

        userName = etName.getText().toString();
        if (TextUtils.isEmpty(userName)) {
            etName.setError(errorMessage);
            isOk = false;
        } else {
            etName.setError(null);
        }

        userSurname = etSurname.getText().toString();
        if (TextUtils.isEmpty(userSurname)) {
            etSurname.setError(errorMessage);
            isOk = false;
        } else {
            etSurname.setError(null);
        }

        userAge = etAge.getText().toString();
        if (TextUtils.isEmpty(userAge)) {
            etAge.setError(errorMessage);
            isOk = false;
        } else {
            etAge.setError(null);
        }

        if (TextUtils.isEmpty(userGender)) {
            tvGender.setError(errorMessage);
            isOk = false;
        } else {
            tvGender.setError(null);
        }
        return isOk;

    }

    private void clearAllFields() {
        etMail.setText("");
        etMail.setError(null);
        etPass.setText("");
        etPass.setError(null);
        etName.setText("");
        etName.setError(null);
        etSurname.setText("");
        etSurname.setError(null);
        etAge.setText("");
        etAge.setError(null);
        radioGroup.clearCheck();
        userGender = "";
        tvGender.setError(null);
    }

    private void createAccount(final String email, final String password, final String name, final String surname, final String gender, final String age) {

        showProgressDialog();

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            buttonReg.setEnabled(false);
                            userUId = FirebaseAuth.getInstance().getCurrentUser().getUid();
                            databaseReference = FirebaseDatabase.getInstance().getReference().child(USERS_CHILD).child(userUId);
                            FirebaseUser user = mAuth.getCurrentUser();
                            Toast.makeText(RegistrationFormActivity.this, "Create user is successful." + user.getEmail(),
                                    Toast.LENGTH_SHORT).show();

                            databaseReference.push()
                                    .setValue(new User(userUId, email, password, name, surname, gender, age));

                            Intent intent = new Intent(RegistrationFormActivity.this, Main2Activity.class);
                            startActivity(intent);
                            finish();
                        } else {
                            buttonReg.setEnabled(true);
                            Toast.makeText(RegistrationFormActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                        hideProgressDialog();
                    }
                });
    }

    private void showDialogInfo() {
        FragmentManager manager = getSupportFragmentManager();
        DialogInfo dialogInfo = new DialogInfo();
        FragmentTransaction transaction = manager.beginTransaction();
        dialogInfo.show(transaction, "dialog");
    }
}
