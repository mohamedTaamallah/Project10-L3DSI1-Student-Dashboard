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
    private ArrayList<Image> Images;
    private Activity parentActivity;
    private DataBaseHandler db;
    private Image image;

    public ImageAdapter(Context context, ArrayList<String> titre, ArrayList<Image> images, Activity parentActivity) {
        this.context = context;
        this.titre = titre;
        Images = images;
        this.parentActivity=parentActivity;

    }
    public ImageAdapter(Context context, Image image, Activity parentActivity) {
        this.context = context;
        this.image =image;
        this.parentActivity=parentActivity;

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



    }

    public Boolean deleteItem(int id)
    {  Boolean resultat= false;
        if(this.Images.remove(id)!=null)
            resultat=true;
        notifyDataSetChanged();
        return  resultat;

    }
    public void updateImage(String id , String desc)
    {
        db.updateImage(this.image.getImage_id(),desc);
        notifyDataSetChanged();
    }


    @Override
    public int getItemCount() {
        return Images.size();
    }

    public class MyviewHolder2 extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {
        ImageView imageView;
        TextView desc;
        ImageView delete;

        public MyviewHolder2(@NonNull View itemView) {
            super(itemView);
            desc = itemView.findViewById(R.id.description);
            delete = itemView.findViewById(R.id.delete);
            imageView = itemView.findViewById(R.id.Image);
            itemView.setOnCreateContextMenuListener(this);
        }


        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            int test = this.getAdapterPosition();
            menu.add(this.getAdapterPosition(),test,0,"View photo ");
            menu.add(this.getAdapterPosition(),test,0,"delete");
            notifyDataSetChanged();
        }
    }




}
