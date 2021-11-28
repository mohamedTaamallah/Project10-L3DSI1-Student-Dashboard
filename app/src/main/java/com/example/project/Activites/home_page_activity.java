package com.example.project.Activites;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project.Adapters.MyContextApp;
import com.example.project.Model.Matiere;
import com.example.project.Adapters.MyAdapter;
import com.example.project.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class home_page_activity extends AppCompatActivity  {

    DatabaseReference mDataRef;

    MyAdapter.RecycleViewClickListener clickListener;
    MyAdapter.RecyclerViewLongClick longClickListener;
    MyContextApp appContext;

    RecyclerView recyclerView;
    ArrayList<Matiere> mats;
    MyAdapter myAdapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_page);
        setTitle("Dashboard");
        appContext = (MyContextApp)getApplicationContext();
        mDataRef = FirebaseDatabase.getInstance().getReference().child("Etudiant").child(appContext.getUid());
        //FloatingActionButton BtnAdd = findViewById(R.id.AddButton);



        mats = new ArrayList<>();

        recyclerView = findViewById(R.id.recycleView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.hasFixedSize();
        mDataRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mats.clear();

                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    Matiere mat = postSnapshot.getValue(Matiere.class);
                    mat.setId(postSnapshot.getKey());
                    mats.add(mat);
                }
                setOnclickListener();
                myAdapter = new MyAdapter(getApplicationContext(), mats, clickListener, longClickListener, mDataRef);
                recyclerView.setAdapter(myAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                //Toast.makeText(home_page_activity.this, "Fail to get data.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void AddMatierial(View v)
    {
        Intent intent = new Intent(this, AddMatierActivity.class);
        startActivity(intent);
    }
    private void setOnclickListener() {
        clickListener = new MyAdapter.RecycleViewClickListener() {
            @Override
            public void onClick(View v, int positon) {
                Intent intent = new Intent(getApplicationContext(), MatiereDetailsActivity.class);
                // ASSIGNING DATA TO CONTEXT
                appContext.setMatiere(mats.get(positon));
                startActivity(intent);
            }
        };
    }
    private void setOnLongClickListener() {
        longClickListener = new MyAdapter.RecyclerViewLongClick() {
            @Override
            public boolean onItemLongClicked(View v, int position) {
                showDeleteDataDialog();
                return true;
            }
        };
    }
    public void showDeleteDataDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(home_page_activity.this);
        builder.setTitle("Delete");
        builder.setTitle("Are you sure you want to delete this ?");

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                System.out.println("Clicked YES");

            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                System.out.println("Clicked NOOO");
                dialog.dismiss();
            }
        });
        builder.create().show();
    }

    // menu configuration
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.logout){
            logout(appContext);
        }else if(id == R.id.btnUpload){
            startActivity(new Intent(this, Upload_image_Activity.class));
        }else if(id == R.id.BtnDates){
            startActivity(new Intent(this, liste_important_dates.class));
        }
        return super.onOptionsItemSelected(item);
    }
    //Logout
    public void logout(Context context) {
        FirebaseAuth.getInstance().signOut();
        GoogleSignIn.getClient(this, new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).build())
                .signOut().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                startActivity(new Intent(context, Start_Screen_Activity.class));
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(context, "Signout Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }
}


