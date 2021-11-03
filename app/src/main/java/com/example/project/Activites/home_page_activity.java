package com.example.project.Activites;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project.Model.Matiere;
import com.example.project.Adapters.MyAdapter;
import com.example.project.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class home_page_activity extends AppCompatActivity  {

    DatabaseReference mDataRef  ;

    RecyclerView recyclerView;
    ArrayList<Matiere> mats;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_page);
        mDataRef = FirebaseDatabase.getInstance().getReference("Etudiant");
        //FloatingActionButton BtnAdd = findViewById(R.id.AddButton);

        mats = new ArrayList<>();

        recyclerView = findViewById(R.id.recycleView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.hasFixedSize();
        mDataRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                mats.clear();
                GenericTypeIndicator<ArrayList<Matiere>> t = new GenericTypeIndicator<ArrayList<Matiere>>() {};
                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                  Matiere mat = postSnapshot.getValue(Matiere.class);
                System.out.println(mat);
                mats.add(mat);
                }

                MyAdapter myAdapter = new MyAdapter(getApplicationContext(), mats);
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
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }


}


