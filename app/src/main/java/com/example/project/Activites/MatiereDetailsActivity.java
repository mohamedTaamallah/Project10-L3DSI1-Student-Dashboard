package com.example.project.Activites;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.etebarian.meowbottomnavigation.MeowBottomNavigation;
import com.example.project.AboutFragment;
import com.example.project.Adapters.MyContextApp;
import com.example.project.HomeFragment;
import com.example.project.NotificationFragment;
import com.example.project.R;

public class MatiereDetailsActivity extends AppCompatActivity {

    MyContextApp app;
    MeowBottomNavigation bottomNavigation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_matiere_details);
        app = (MyContextApp) getApplicationContext();

        setTitle("");
/*        ((TextView)findViewById(R.id.matName)).setText(app.getMatiere().getName());

        System.out.println("--------------------------------");
        System.out.println("Nom Matiere: "+app.getMatiere().getName());
        System.out.println("Mat ID: "+ app.getMatiere().getId());
        System.out.println("--------------------------------");

*/
        bottomNavigation = findViewById(R.id.bottom_navigation);
        // add menu item
        bottomNavigation.add(new MeowBottomNavigation.Model(1, R.drawable.ic_notification));
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
                        fragment = new NotificationFragment();
                        break;
                    case 2:
                        //initilize Home fragment
                        fragment = new HomeFragment();
                        break;
                    case 3:
                        //initilize About fragment
                        fragment = new AboutFragment();
                        break;
                }
                loadFragment(fragment);
            }
        });

        //set notificiton count
        bottomNavigation.setCount(1, "10");
        // set Home fragmetn initially selected
        bottomNavigation.show(2, true);

        bottomNavigation.setOnClickMenuListener(new MeowBottomNavigation.ClickListener() {
            @Override
            public void onClickItem(MeowBottomNavigation.Model item) {
                Toast.makeText(getApplicationContext(), "You Clicked"+item.getId(), Toast.LENGTH_SHORT).show();
            }
        });
        bottomNavigation.setOnReselectListener(new MeowBottomNavigation.ReselectListener() {
            @Override
            public void onReselectItem(MeowBottomNavigation.Model item) {
                Toast.makeText(getApplicationContext(), "You Reselected"+item.getId(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadFragment(Fragment fragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frame_layout, fragment)
                .commit();
    }
}