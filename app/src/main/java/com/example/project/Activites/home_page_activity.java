package com.example.project.Activites;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

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
        mDataRef = FirebaseDatabase.getInstance().getReference("Etudiant");
        //FloatingActionButton BtnAdd = findViewById(R.id.AddButton);

        appContext = (MyContextApp)getApplicationContext();

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
                myAdapter = new MyAdapter(getApplicationContext(), mats, clickListener, longClickListener);
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
}


