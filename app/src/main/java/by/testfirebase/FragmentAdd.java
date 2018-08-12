package by.testfirebase;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import by.testfirebase.dataModel.Article;

public class FragmentAdd extends Fragment{

    private EditText etText;
    private Button buttonAdd;
    public static final String MESSAGES_CHILD = "messages";
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private DatabaseReference databaseReference = database.getReference();
    private RecyclerView recyclerView;
    private List<Article> articleArrayList = new ArrayList<>();
    private ShowAdapter showAdapter;

    private Article article;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_add, container, false);
        Log.e("AAAA", "showFragOnCreateView");
        etText = rootView.findViewById(R.id.editTextMessage);
        buttonAdd = rootView.findViewById(R.id.buttonAdd);
        recyclerView = rootView.findViewById(R.id.recyclerView);

//        recyclerView.setLayoutManager(new LinearLayoutManager(rootView.getContext()));
//        showAdapter = new ShowAdapter(articleArrayList);
//        recyclerView.setAdapter(showAdapter);

        Log.e("AAAA", "showFragOnCreateView after GetActiviry");

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Article article = new Article(FirebaseAuth.getInstance().getCurrentUser().getEmail(), etText.getText().toString());
                databaseReference.push().setValue(article);
                etText.setText("");
            }
        });

//        databaseReference.addChildEventListener(new ChildEventListener() {
//            @Override
//            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//                article = dataSnapshot.getValue(Article.class);
//                articleArrayList.add(article);
//                showAdapter.notifyDataSetChanged();
//            }
//
//            @Override
//            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//
//            }
//
//            @Override
//            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
//                article = dataSnapshot.getValue(Article.class);
//                articleArrayList.remove(article);
//                showAdapter.notifyDataSetChanged();
//            }
//
//            @Override
//            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });

        return rootView;
    }
}
