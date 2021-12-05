package com.example.project.Adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project.Model.Image;
import com.example.project.R;
import com.example.project.SQL_lite.DataBaseHandler;

import java.util.ArrayList;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.MyviewHolder2> {

    private Context context;
    private ArrayList<String> titre;
    private LayoutInflater layoutInflater;
    private ArrayList<Image> Images;
    private Activity parentActivity;
    private RecycleViewClickListener clickListener;



    public ImageAdapter(Context context, ArrayList<String> titre, ArrayList<Image> images, Activity parentActivity, ImageAdapter.RecycleViewClickListener clickListener) {
        this.context = context;
        this.titre = titre;
        this.layoutInflater = layoutInflater;
        Images = images;
        this.parentActivity=parentActivity;
        this.clickListener = clickListener;

    }


    @NonNull
    @Override
    public ImageAdapter.MyviewHolder2 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(this.context);
        View view = layoutInflater.inflate(R.layout.image_card,parent,false);
        return new MyviewHolder2(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageAdapter.MyviewHolder2 holder, @SuppressLint("RecyclerView") int position) {
        holder.desc.setText(Images.get(position).getTitre());
        DataBaseHandler db = new DataBaseHandler(context);
        Image imagess= Images.get(position);
        byte[] ima = imagess.getImage();

        holder.imageView.setImageBitmap(BitmapFactory.decodeByteArray(ima, 0, ima.length));
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog(Images.get(position),db,position);
                notifyDataSetChanged();
            }
        });



    }

    @Override
    public int getItemCount() {
        return Images.size();
    }

    public class MyviewHolder2 extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView imageView;
        TextView desc;
        ImageView delete ;
        public MyviewHolder2(@NonNull View itemView) {
            super(itemView);
            desc = itemView.findViewById(R.id.description);
            delete =itemView.findViewById(R.id.delete);
            imageView = itemView.findViewById(R.id.Image);
        }

        @Override
        public void onClick(View view) {
            clickListener.onClick(itemView, getAdapterPosition());
        }

    }
    public interface RecycleViewClickListener {
        void onClick(View v, int positon);
    }





    private void alertDialog(Image image,DataBaseHandler db ,int position) {
        AlertDialog.Builder dialog=new AlertDialog.Builder(context);
        dialog.setMessage("Supprimer image");
        dialog.setTitle("Vous etes entrain de supprimer une photo ");


        dialog.setPositiveButton("valider",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int which) {
                        db.deleteProduct(image.getImage_id());
                        Images.remove(position);
                        notifyDataSetChanged();

                    }
                });
        dialog.setNegativeButton("Annuler",new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        AlertDialog alertDialog=dialog.create();
        alertDialog.show();
        notifyDataSetChanged();

    }
}
