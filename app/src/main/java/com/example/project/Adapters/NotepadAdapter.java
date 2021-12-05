package com.example.project.Adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project.Model.Image;
import com.example.project.Model.Notepad;
import com.example.project.R;
import com.example.project.SQL_lite.DataBaseHandler;

import java.util.ArrayList;

public class NotepadAdapter extends RecyclerView.Adapter<NotepadAdapter.MyviewHolder2> {

    private Context context;
    private LayoutInflater layoutInflater;
    private ArrayList<Notepad> Note;
    private Activity parentActivity;
    private RecycleViewClickListener clickListener;

    public NotepadAdapter(Context context, ArrayList<Notepad> note, Activity parentActivity, NotepadAdapter.RecycleViewClickListener clickListener) {
        this.context = context;
        this.layoutInflater = layoutInflater;
        this.Note = note;
        this.parentActivity = parentActivity;
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public NotepadAdapter.MyviewHolder2 onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        LayoutInflater layoutInflater = LayoutInflater.from(this.context);
        View view = layoutInflater.inflate(R.layout.notepad_card,parent,false);
        return new NotepadAdapter.MyviewHolder2(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotepadAdapter.MyviewHolder2 holder, @SuppressLint("RecyclerView") int position) {
        //Notepad n = Note.get(position);
        if(Note.get(position)!=null) {
            holder.desc.setText(Note.get(position).getTexte());
        }


        /*
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog(Images.get(position),db,position);
                notifyDataSetChanged();
            }
        });*/

    }

    @Override
    public int getItemCount() {
        return Note.size();

    }
    public class MyviewHolder2 extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView desc;
        //ImageView delete ;
        public MyviewHolder2(@NonNull View itemView) {
            super(itemView);
            desc = itemView.findViewById(R.id.texte);
            //delete =itemView.findViewById(R.id.supprimer);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            clickListener.onClick(itemView, getAdapterPosition());
        }

    }
    public interface RecycleViewClickListener {
        void onClick(View v, int positon);
    }




/*
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
*/

}
