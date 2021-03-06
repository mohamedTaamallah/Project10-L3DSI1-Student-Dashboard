package com.example.project.Activites;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Environment;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.project.Adapters.ImageAdapter;
import com.example.project.Model.Image;
import com.example.project.R;
import com.example.project.SQL_lite.DataBaseHandlerImage;

import java.io.File;
import java.io.FileOutputStream;

public class ViewPhotoActivity extends AppCompatActivity {
    Image selectedItem;
    DataBaseHandlerImage db;
    ImageAdapter imageAdapter;
    String matiere_id;
    private ScaleGestureDetector scaleGestureDetector;
    private float mScaleFactor = 1.0f;
    ImageView image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_photo);
        selectedItem = ListePhotoActivity.getSelectedImage();

        EditText desc = findViewById(R.id.desc);
         image = findViewById(R.id.image_edit);
        scaleGestureDetector = new ScaleGestureDetector(this, new ScaleListener());
        db=new DataBaseHandlerImage(this);

        byte[] bytes = selectedItem.getImage();

        Intent i = getIntent();
        matiere_id = i.getStringExtra("matiere");

        desc.setText(selectedItem.getTitre());
        image.setImageBitmap(BitmapFactory.decodeByteArray(bytes, 0, bytes.length));

        imageAdapter = new ImageAdapter(this,selectedItem,this);
        TextView upload = findViewById(R.id.text_upload_photo);
        Button update = findViewById(R.id.button_update);

        ActivityCompat.requestPermissions(ViewPhotoActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},1);
        ActivityCompat.requestPermissions(ViewPhotoActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},1);

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveToGallery(image);
                Toast.makeText(ViewPhotoActivity.this," Image enregistr??e dans le dossier Pictures", Toast.LENGTH_SHORT).show();

            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String description = desc.getText().toString();
                db.updateImage(selectedItem.getImage_id(),description);
                Toast.makeText(ViewPhotoActivity.this," Description modifi??e  ", Toast.LENGTH_SHORT).show();
                Intent i =new Intent(ViewPhotoActivity.this, home_page_activity.class);

                i.putExtra("matiere_id",matiere_id);
                startActivity(i);
            }
        });


    }
    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {
        scaleGestureDetector.onTouchEvent(motionEvent);
        return true;
    }
    private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
        @Override
        public boolean onScale(ScaleGestureDetector scaleGestureDetector) {
            mScaleFactor *= scaleGestureDetector.getScaleFactor();
            mScaleFactor = Math.max(0.1f, Math.min(mScaleFactor, 10.0f));
            image.setScaleX(mScaleFactor);
            image.setScaleY(mScaleFactor);
            return true;
        }
    }
    private void saveToGallery(ImageView imageView){
        BitmapDrawable bitmapDrawable = (BitmapDrawable) imageView.getDrawable();
        Bitmap bitmap = bitmapDrawable.getBitmap();

        FileOutputStream outputStream = null;
        File file = Environment.getExternalStorageDirectory();
        File dir = new File(file.getAbsolutePath() + "/Pictures");
        dir.mkdirs();

        String filename = String.format("%d.jpeg",System.currentTimeMillis());
        File outFile = new File(dir,filename);
        try{
            outputStream = new FileOutputStream(outFile);
        }catch (Exception e){
            e.printStackTrace();
        }
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,outputStream);
        try{
            outputStream.flush();
        }catch (Exception e){
            e.printStackTrace();
        }
        try{
            outputStream.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

}