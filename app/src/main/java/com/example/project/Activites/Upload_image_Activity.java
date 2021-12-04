package com.example.project.Activites;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.project.Adapters.MyContextApp;
import com.example.project.Fragments.AboutFragment;
import com.example.project.Model.Image;
import com.example.project.R;
import com.example.project.SQL_lite.DataBaseHandler;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;

public class Upload_image_Activity extends AppCompatActivity {


    private static final int PICK_IMAGE_REQUEST = 1;
    private static final int REQUEST_IMAGE_CAPTURE=101;

    private Button mButtonChooseImage;
    private Button mButtonUpload;
    private TextView mTextViewShowUploads;
    private EditText mEditTextFileName;
    private ImageView mImageView;
    private ProgressBar mProgressBar;
    private DataBaseHandler db ;
    private Uri mImageUri;

    private StorageReference mStorageRef;
    private DatabaseReference mDatabaseRef;

    private StorageTask mUploadTask;
    MyContextApp appContext;
    String matiere_id;
    private static ArrayList<Image> list_image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.upload_image);
        setTitle("Upload image");
        db =new DataBaseHandler(this);

        mButtonChooseImage = findViewById(R.id.button_choose_image);
        mButtonUpload = findViewById(R.id.button_upload);
        mTextViewShowUploads = findViewById(R.id.text_view_show_uploads);
        mEditTextFileName = findViewById(R.id.edit_text_file_name);
        mImageView = findViewById(R.id.image_view);
        mProgressBar = findViewById(R.id.progress_bar);
        appContext = (MyContextApp)getApplicationContext();

        mStorageRef = FirebaseStorage.getInstance().getReference();
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("Etudiant").child(appContext.getUid());

        mButtonChooseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();
            }
        });

        mButtonUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mUploadTask != null && mUploadTask.isInProgress()) {
                    Toast.makeText(Upload_image_Activity.this, matiere_id+ " ", Toast.LENGTH_SHORT).show();
                } else {
                     uploadFile();
                    Intent intent=getIntent();

                    matiere_id = intent.getStringExtra("matiere");



                    Intent i = new Intent (Upload_image_Activity.this, AboutFragment.class);
                    startActivity(i);
                    i.putExtra("mat_id",matiere_id);

                }
            }
        });

        mTextViewShowUploads.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takePicture();
            }
        });
    }

    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }
    private void takePicture(){
        Intent imageTakeIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if(imageTakeIntent.resolveActivity(getPackageManager())!=null)
        {
            startActivityForResult(imageTakeIntent,REQUEST_IMAGE_CAPTURE);

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            mImageUri = data.getData();
            InputStream inputStream = null;
            try {
                inputStream = getContentResolver().openInputStream(mImageUri);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                mImageView.setImageBitmap(bitmap);
        }
        else if(requestCode==REQUEST_IMAGE_CAPTURE&&resultCode==RESULT_OK )
        {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            mImageView.setImageBitmap(imageBitmap);

        }
    }





    private void uploadFile() {
        if (mImageView != null) {
            Intent intent=getIntent();
             matiere_id = intent.getStringExtra("matiere");
             long photo_id = (new Date().getTime())/1000;

            if(db.insertImage(String.valueOf(photo_id),mEditTextFileName.getText().toString(),imageViewToByte(mImageView),String.valueOf(matiere_id)))
            {
                Toast.makeText(Upload_image_Activity.this, "image enregistrée ", Toast.LENGTH_SHORT).show();

            }
            else{
                Toast.makeText(Upload_image_Activity.this, "echec de enregistrement ", Toast.LENGTH_SHORT).show();

            }
        }
        else{

            Toast.makeText(Upload_image_Activity.this, "Entrer votre image  ", Toast.LENGTH_SHORT).show();
        }
    }
    public static byte[] imageViewToByte(ImageView image) {
        Bitmap bitmap = ((BitmapDrawable)image.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }

    public static ArrayList<Image> getList_image() {
        return list_image;
    }
}