package by.testfirebase;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import by.testfirebase.dataModel.Article;

public class AddAndShowActivity extends AppCompatActivity {

    private EditText etText;
    private Button buttonAdd;
    public static final String MESSAGES_CHILD = "messages";
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = database.getReference();
    private RecyclerView recyclerView;
    private List<Article> articleArrayList = new ArrayList<>();
    private ShowAdapter showAdapter;

    private Article article;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_and_show);

        etText = findViewById(R.id.editTextMessage);
        buttonAdd = findViewById(R.id.buttonAdd);
        recyclerView = findViewById(R.id.recyclerView);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        showAdapter = new ShowAdapter(articleArrayList);
        recyclerView.setAdapter(showAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Article article = new Article(FirebaseAuth.getInstance().getCurrentUser().getEmail(), etText.getText().toString());
                databaseReference.push().setValue(article);
                etText.setText("");
            }
        });

        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                article = dataSnapshot.getValue(Article.class);
                articleArrayList.add(article);
                showAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                article = dataSnapshot.getValue(Article.class);
                articleArrayList.remove(article);
                showAdapter.notifyDataSetChanged();
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
