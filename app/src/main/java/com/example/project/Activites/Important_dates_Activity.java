package com.example.project.Activites;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.project.Adapters.MyContextApp;
import com.example.project.Model.AlarmReciever;
import com.example.project.Model.date;
import com.example.project.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class Important_dates_Activity extends AppCompatActivity {
    MyContextApp appContext;
    String time ;
    long IdDate =0;
    ArrayList<date> liste_dates =new ArrayList<>() ;
    String selectedDate;
    String id;
    EditText TxtDescription;
    Button BtnAdd;

    AlarmManager alarmManager;
    Calendar calendar;
    SimpleDateFormat sdf;
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.important_dates);
        setTitle("Important dates");
        // NOTIFICATION CHANNEL
        createNotificationChannel();

        Button BtnAdd= findViewById(R.id.button_Add);
        TxtDescription = findViewById(R.id.date_description);
        TimePicker timePicker = findViewById(R.id.timePicker);
        CalendarView calendar =findViewById(R.id.calendarView);
        appContext = (MyContextApp)getApplicationContext();

        sdf = new SimpleDateFormat("dd/MM/yyyy");

        DatabaseReference reff  = FirebaseDatabase.getInstance().getReference("Etudiant").child(appContext.getUid()).child("data").child("dates");


        reff.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    IdDate = snapshot.getChildrenCount()+1;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                month+=1;
                selectedDate = dayOfMonth+"/"+month+"/"+year;
            }
        });

        BtnAdd.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {



                if(check(TxtDescription)==true)
                {
                    String description = TxtDescription.getText().toString();
                    Toast.makeText(Important_dates_Activity.this,"Date ajoutée", Toast.LENGTH_SHORT).show();
                    String hour =String.valueOf(timePicker.getHour());
                    String minutes =String.valueOf(timePicker.getMinute());
                    time=hour+":"+minutes;
                    date date = new date(description,time,selectedDate);
                    id="Date :"+IdDate;

                    reff.child(id).setValue(date);

                    // SET NOTIFICATION ALARM
                    setAlarm(hour, minutes, description);
                }

            }
        });

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void setAlarm(String hour, String minute, String content) {
        calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(hour));
        calendar.set(Calendar.MINUTE, Integer.parseInt(minute));
        calendar.set(Calendar.SECOND, 0);
        try {
            Toast.makeText(Important_dates_Activity.this, selectedDate, Toast.LENGTH_SHORT).show();
            if(selectedDate!=null)
            calendar.setTime(sdf.parse(selectedDate));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(Important_dates_Activity.this, AlarmReciever.class);
        intent.putExtra("title", "Student Dashboard Reminder");
        intent.putExtra("content", content);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(Important_dates_Activity.this, 0, intent,0);
        alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                AlarmManager.INTERVAL_DAY, pendingIntent);

        Toast.makeText(Important_dates_Activity.this, "Reminder Set", Toast.LENGTH_SHORT).show();
    }

    public Boolean check (EditText description ){
        Boolean result =true;
        if(TextUtils.isEmpty(description.getText()))
        {
            description.setError("Veuillez entrer la description");
            result=false;
        }
        return result;
    }

    // NOTIFICATION ALARM
    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Notification channel";
            String description = "Channel for Alarm manager";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel("Student", name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}
