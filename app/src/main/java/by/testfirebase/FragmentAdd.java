package by.testfirebase;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import by.testfirebase.dataModel.Article;

public class FragmentAdd extends Fragment{

    private EditText etText;
    private Button buttonAdd;
    public static final String MESSAGES_CHILD = "messages";
    private DatabaseReference databaseReference;
    private String userUId;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_add, container, false);

        etText = rootView.findViewById(R.id.editTextMessage);
        buttonAdd = rootView.findViewById(R.id.buttonAdd);

        if (FirebaseAuth.getInstance() != null) {
            if (FirebaseAuth.getInstance().getCurrentUser() != null) {
                if (FirebaseAuth.getInstance().getCurrentUser().getUid() != null) {
                    userUId = FirebaseAuth.getInstance().getCurrentUser().getUid();
                    databaseReference = FirebaseDatabase.getInstance().getReference().child(MESSAGES_CHILD).child(userUId);

                    buttonAdd.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Article article = new Article(userUId, etText.getText().toString());
                            databaseReference.push().setValue(article);
                            etText.setText("");
                        }
                    });

                }}}
        return rootView;
    }
}
