package com.example.project.Activites;

import android.app.AlertDialog;
import android.app.PendingIntent;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project.Adapters.ImportantDateAdapter;
import com.example.project.Adapters.MyContextApp;
import com.example.project.Model.Image;
import com.example.project.Model.date;
import com.example.project.R;
import com.example.project.SQL_lite.DataBaseHandler;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class liste_important_dates extends AppCompatActivity {
    Button addDate;
    RecyclerView recyclerView;
    DatabaseReference mDataRef;
    MyContextApp appContext;
    ArrayList<date> liste_dates;
    private String CHANNEL_ID = "My Notification";
    ImportantDateAdapter DateAdapter;
    public ArrayList<date> getListe_dates() {
        return liste_dates;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.liste_important_dates);
        setTitle("Important date liste ");

        //addDate = findViewById(R.id.AddDate);
        recyclerView = (RecyclerView) findViewById(R.id.recycleView);
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager layout = new LinearLayoutManager(liste_important_dates.this);
        recyclerView.setLayoutManager(layout);

        liste_dates = new ArrayList<>();
        appContext = (MyContextApp) getApplicationContext();

        mDataRef = FirebaseDatabase.getInstance().getReference().child("Etudiant").child(appContext.getUid()).child("data").child("dates");

        mDataRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                liste_dates.clear();

                for (DataSnapshot postSnapshot : snapshot.getChildren()) {
                    date dat = postSnapshot.getValue(date.class);
                    dat.setId(postSnapshot.getKey());
                    liste_dates.add(dat);


                }
                 DateAdapter = new ImportantDateAdapter(getApplicationContext(), liste_dates, mDataRef);
                recyclerView.setAdapter(DateAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                //Toast.makeText(home_page_activity.this, "Fail to get data.", Toast.LENGTH_SHORT).show();
            }
        });

    }
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        Boolean resultat= false;
        if(item.getTitle().equals("delete"))
        {
          alertDialog(item.getItemId());

        }
        else
        {



        }

        return  resultat;
    }
    public void add(View v) {
        Intent intent = new Intent(liste_important_dates.this, Important_dates_Activity.class);
        startActivity(intent);
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void detect(ArrayList<date> l)
    {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        String now = LocalDateTime.now().format(dtf);
        LocalDate  d1 = LocalDate.parse(now, dtf);
        for (int i = 0; i < l.size(); i++) {
            String test = l.get(i).getDate();
            LocalDate localDate2 = LocalDate.parse(test);
            long days = Period.between(localDate2, d1).getDays();

            if(days==1){
                Toast.makeText(this, l.get(i).getDate(), Toast.LENGTH_SHORT).show();

            }

        }




    }
    public void notif()
    {
        Intent intent = new Intent(this, liste_important_dates.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_notification)
                .setContentTitle("My notification")
                .setContentText("Hello World!")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                // Set the intent that will fire when the user taps the notification
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);
    }


    private void alertDialog(int position) {
        AlertDialog.Builder dialog=new AlertDialog.Builder(this);
        dialog.setMessage("Supprimer date");
        dialog.setTitle("Vous etes entrain de supprimer une date importante ");


        dialog.setPositiveButton("Supprimer",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int which) {
                        String index = liste_dates.get(position).getId();
                        Toast.makeText(liste_important_dates.this, "Evenement supprimée", Toast.LENGTH_SHORT).show();
                        mDataRef.child(index).removeValue();
                        DateAdapter.deleteItem(position);
                    }
                });
        dialog.setNegativeButton("Annuler",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        AlertDialog alertDialog=dialog.create();
        alertDialog.show();
    }
}