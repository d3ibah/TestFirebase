package by.testfirebase;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import by.testfirebase.dataModel.Article;

public class FragmentShowBlog extends Fragment {

    public static final String MESSAGES_CHILD = "messages";
    private DatabaseReference databaseReference;
    private RecyclerView recyclerView;
    private Button buttonGoToFragmentAdd;
    private LinearLayoutManager linearLayoutManager;
    private List<Article> articleArrayList = new ArrayList<>();
    private ShowAdapter showAdapter;
    private String userUId;

    private Article article;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable final Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_show_blog, container, false);

        recyclerView = rootView.findViewById(R.id.recyclerView);
        buttonGoToFragmentAdd = rootView.findViewById(R.id.buttonGoToAdd);

        getActivity().setTitle(R.string.home);

        buttonGoToFragmentAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showFragmentAdd();
            }
        });

        if (getActivity().getApplicationContext() != null) {
            linearLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
            linearLayoutManager.setReverseLayout(true);
            linearLayoutManager.setStackFromEnd(true);
        }

        recyclerView.setLayoutManager(linearLayoutManager);
        showAdapter = new ShowAdapter(articleArrayList);
        recyclerView.setAdapter(showAdapter);

        if (FirebaseAuth.getInstance() != null) {
            if (FirebaseAuth.getInstance().getCurrentUser() != null) {
                if (FirebaseAuth.getInstance().getCurrentUser().getUid() != null) {
                    userUId = FirebaseAuth.getInstance().getCurrentUser().getUid();
                    databaseReference = FirebaseDatabase.getInstance().getReference().child(MESSAGES_CHILD).child(userUId);
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
        }


        return rootView;
    }

    public void showFragmentAdd() {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        FragmentAdd fragment = new FragmentAdd();
        fragmentTransaction.replace(R.id.container, fragment, null).addToBackStack(null).commit();
    }
}
