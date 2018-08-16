package by.testfirebase;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import by.testfirebase.dataModel.User;


public class Main2Activity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawer;

    private TextView tvUserName, tvUserMail;

    private FirebaseAuth mAuth;
    private DatabaseReference databaseReference;
    public static final String USERS_CHILD = "usersList";
    private String userUId;

    private Fragment fragment = null;
    private Class fragmentClass = null;
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        fragmentManager = getSupportFragmentManager();

        mAuth = FirebaseAuth.getInstance();
        userUId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        databaseReference = FirebaseDatabase.getInstance().getReference().child(USERS_CHILD).child(userUId);

        if (savedInstanceState == null) {
            showFragmentBlog();
        }

        View headerLayout = navigationView.getHeaderView(0);
        tvUserMail = headerLayout.findViewById(R.id.tvUserMail);
        tvUserName = headerLayout.findViewById(R.id.tvUserName);

        changeNavHeader();

    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
                super.onBackPressed();
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            fragmentClass = FragmentShowBlog.class;

        } else if (id == R.id.nav_add) {
            fragmentClass = FragmentAdd.class;

        } else if (id == R.id.nav_profile) {
            fragmentClass = FragmentUserInfo.class;

        } else if (id == R.id.nav_logout) {
            fragmentManager.beginTransaction().detach(fragment).commit();
            mAuth.signOut();
            Intent intent = new Intent(Main2Activity.this, AuthActivity.class);
            startActivity(intent);
            finish();
        }

        try {
            fragment = (Fragment)fragmentClass.newInstance();
        }
        catch (Exception e){
            e.printStackTrace();
        }

        fragmentManager.beginTransaction().replace(R.id.container, fragment).addToBackStack(null).commit();
        item.setCheckable(true);
        setTitle(item.getTitle());

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void showFragmentBlog() {
        fragmentClass = FragmentShowBlog.class;
        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }

        fragmentManager.beginTransaction().add(R.id.container, fragment).commit();
    }

    private void changeNavHeader() {
        tvUserMail.setText(mAuth.getCurrentUser().getEmail());

        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                User user = dataSnapshot.getValue(User.class);
                String fullName = (user.getName() + " " + user.getSurname());
                tvUserName.setText(fullName);
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
    }
}
