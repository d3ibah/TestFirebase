package by.testfirebase;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import by.testfirebase.dataModel.User;

public class FragmentUserInfo extends Fragment {

    TextView email, name, surname, gender, age;
    private FirebaseDatabase database;
    private DatabaseReference databaseReference;
    public static final String USERS_CHILD = "usersList";
    User user;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragmet_user_info, container, false);

        email = rootView.findViewById(R.id.tvInfoMail);
        name = rootView.findViewById(R.id.tvInfoName);
        surname = rootView.findViewById(R.id.tvInfoSurname);
        gender = rootView.findViewById(R.id.tvInfoGender);
        age = rootView.findViewById(R.id.tvInfoAge);

        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference();

        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//                user = dataSnapshot.getValue(User.class);
//                setUserInfo(user);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        return rootView;
    }

    private void setUserInfo(User user) {
        email.setText(user.getEmail());
        name.setText(user.getName());
        surname.setText(user.getSurname());
        gender.setText(user.getGender());
        age.setText(user.getAge());
    }

}
