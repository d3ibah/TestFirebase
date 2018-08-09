package by.testfirebase;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import by.testfirebase.dataModel.Article;

public class AddAndShowActivity extends AppCompatActivity {

    EditText etText;
    Button buttonAdd;
    TextView textDate, textIdUser, textMessage;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_and_show);

        etText = findViewById(R.id.editTextMessage);
        buttonAdd = findViewById(R.id.buttonAdd);
        textDate = findViewById(R.id.textViewDate);
        textIdUser = findViewById(R.id.textViewIdUser);
        textMessage = findViewById(R.id.textViewMessage);
    }

    @Override
    protected void onStart() {
        super.onStart();

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FirebaseDatabase.getInstance().getReference().push()
                        .setValue(new Article(FirebaseAuth.getInstance().getCurrentUser().getEmail(), etText.getText().toString()));
                etText.setText("");
            }
        });
    }
}
