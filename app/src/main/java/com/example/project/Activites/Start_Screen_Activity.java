package com.example.project.Activites;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.VideoView;
import android.view.View;
import android.widget.Toast;

import com.example.project.Adapters.MyContextApp;
import com.example.project.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Start_Screen_Activity extends AppCompatActivity {
    public static final int GOOGLE_SIGN_IN_CODE = 10005;
    private Handler handler = new Handler();

    SignInButton signIn;
    GoogleSignInOptions gso;
    GoogleSignInClient signInClient;
    GoogleSignInAccount signInAccount;
    FirebaseAuth firebaseAuth;
    DatabaseReference reff;
    MyContextApp appContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_screen);

        appContext = (MyContextApp)getApplicationContext();
        reff  = FirebaseDatabase.getInstance().getReference("Etudiant");
        firebaseAuth = FirebaseAuth.getInstance();
        appContext.setUid(firebaseAuth.getUid());

        signIn = findViewById(R.id.signIn);


        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("869953578810-gnin42ul8el5a1pqcbj70gv6knd5fsdc.apps.googleusercontent.com")
                .requestEmail()
                .build();
        signInClient = GoogleSignIn.getClient(this, gso);

        signInAccount = GoogleSignIn.getLastSignedInAccount(this);

        //logo
        VideoView videoView = (VideoView) findViewById(R.id.videoView);
        videoView.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.logo);
        videoView.start();
        //team
        VideoView videoView2 = (VideoView) findViewById(R.id.videoView2);
        videoView2.setVideoPath("android.resource://" + getPackageName() + "/" + R.raw.new_int);
        videoView2.start();


        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sign = signInClient.getSignInIntent();
                startActivityForResult(sign, GOOGLE_SIGN_IN_CODE);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == GOOGLE_SIGN_IN_CODE) {
            Task<GoogleSignInAccount> signInTask = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount signInAcc = signInTask.getResult(ApiException.class);

                AuthCredential authCredential = GoogleAuthProvider.getCredential(signInAcc.getIdToken(), null);
                firebaseAuth.signInWithCredential(authCredential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Toast.makeText(getApplicationContext(), "Your google account is connected", Toast.LENGTH_SHORT).show();
                        appContext.setUid(firebaseAuth.getUid());
                        startActivity(new Intent(getApplicationContext(), home_page_activity.class));
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getApplicationContext(), "Failed to connect", Toast.LENGTH_SHORT).show();
                        System.out.println("failed");
                    }
                });
            } catch (ApiException e) {
                e.printStackTrace();
            }
        }
    }

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if(!isFinishing())
            {
                startActivity(new Intent(getApplicationContext(), home_page_activity.class));
                finish();
            }
        }

    };

    @Override
    protected void onResume() {
        super.onResume();
        if(signInAccount != null || firebaseAuth.getCurrentUser() != null) {
            signIn.setVisibility(View.INVISIBLE);
            appContext.setUid(firebaseAuth.getCurrentUser().getUid());
            Toast.makeText(this, "User is already logged in", Toast.LENGTH_SHORT).show();
            handler.postDelayed(runnable,3000);
        }

    }

    @Override
    protected void onPause() {
        super.onPause();
        handler.removeCallbacks(runnable);
    }

    // LOGOUT METHOD FOR LATER
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
