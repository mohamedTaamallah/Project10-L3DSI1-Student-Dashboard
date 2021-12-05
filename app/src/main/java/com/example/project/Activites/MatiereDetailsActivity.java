package com.example.project.Activites;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.example.project.Fragments.AboutFragment;
import com.example.project.Adapters.MyContextApp;
import com.example.project.Fragments.HomeFragment;
import com.example.project.Fragments.NotificationFragment;
import com.example.project.R;

public class MatiereDetailsActivity extends AppCompatActivity {

    MyContextApp app;
    MeowBottomNavigation bottomNavigation;
    String matiere_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent=getIntent();
        matiere_id = intent.getStringExtra("matiere_id");

        setContentView(R.layout.activity_matiere_details);
        app = (MyContextApp) getApplicationContext();

        setTitle("");

        bottomNavigation = findViewById(R.id.bottom_navigation);
        // add menu item
        bottomNavigation.add(new MeowBottomNavigation.Model(1, R.drawable.ic_baseline_sticky_note));
        bottomNavigation.add(new MeowBottomNavigation.Model(2, R.drawable.ic_home));
        bottomNavigation.add(new MeowBottomNavigation.Model(3, R.drawable.ic_about));

        bottomNavigation.setOnShowListener(new MeowBottomNavigation.ShowListener() {
            @Override
            public void onShowItem(MeowBottomNavigation.Model item) {
                // Initilize fragment
                Fragment fragment = null;
                // check Condition
                switch (item.getId()) {
                    case 1:
                        //initilize notification fragment
                        fragment = new NotificationFragment(matiere_id);
                        break;
                    case 2:
                        //initilize Home fragment
                        fragment = new HomeFragment();
                        //((HomeFragment) fragment).changeText("Hello");
                        break;
                    case 3:
                        //initilize About fragment
                        fragment = new AboutFragment(matiere_id);
                        //Toast.makeText(MatiereDetailsActivity.this, matiere_id+" ", Toast.LENGTH_SHORT).show();

                        break;
                }
                loadFragment(fragment, item);
            }
        });

        //set notificiton count
        //bottomNavigation.setCount(1, "10");
        // set Home fragmetn initially selected
        bottomNavigation.show(2, true);

        bottomNavigation.setOnClickMenuListener(new MeowBottomNavigation.ClickListener() {
            @Override
            public void onClickItem(MeowBottomNavigation.Model item) {



            }
        });
        bottomNavigation.setOnReselectListener(new MeowBottomNavigation.ReselectListener() {
            @Override
            public void onReselectItem(MeowBottomNavigation.Model item) {
                Toast.makeText(getApplicationContext(), "You Reselected"+item.getId(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadFragment(Fragment fragment,MeowBottomNavigation.Model item) {

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frame_layout, fragment)
                .commit();

    }
}