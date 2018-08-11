package by.testfirebase;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class ShowUserActivity extends AppCompatActivity {

    TextView email, name, surname, gender, age;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_user);

        email = findViewById(R.id.tvInfoMail);
        name = findViewById(R.id.tvInfoName);
        surname = findViewById(R.id.tvInfoSurname);
        gender = findViewById(R.id.tvInfoGender);
        age = findViewById(R.id.tvInfoAge);
    }
}
