package com.example.project.Adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project.Model.Notepad;
import com.example.project.R;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

public class NotepadAdapter extends RecyclerView.Adapter<NotepadAdapter.MyviewHolder2> {

    private Context context;
    private LayoutInflater layoutInflater;
    private ArrayList<Notepad> ListNotes;
    private Activity parentActivity;
    private RecycleViewClickListener clickListener;
    DatabaseReference firebaseDatabase;

    public NotepadAdapter(Context context, ArrayList<Notepad> note, Activity parentActivity,DatabaseReference database, NotepadAdapter.RecycleViewClickListener clickListener) {
        this.context = context;
        this.layoutInflater = layoutInflater;
        this.ListNotes = note;
        this.parentActivity = parentActivity;
        this.clickListener = clickListener;
        this.firebaseDatabase = database;
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
        if(ListNotes.get(position)!=null) {
            holder.desc.setText(ListNotes.get(position).getTexte());
        }



        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DeleteNote(position);
                //mDataRef = FirebaseDatabase.getInstance().getReference("Etudiant").child(app.getUid()).child("Matiere").child(app.getMatiere().getId()).child("Notepad");
                /*String index = ListNotes.get(position).getId();
                Toast.makeText(holder.delete.getContext(), "Note supprimée" + position, Toast.LENGTH_SHORT).show();
                firebaseDatabase.child(String.valueOf(index)).removeValue();*/
                notifyDataSetChanged();
            }
        });

    }

    @Override
    public int getItemCount() {
        return ListNotes.size();

    }
    public class MyviewHolder2 extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView desc;
        ImageView delete ;
        public MyviewHolder2(@NonNull View itemView) {
            super(itemView);
            desc = itemView.findViewById(R.id.texte);
            delete = itemView.findViewById(R.id.supprimer);
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





    private void alertDialog(int position) {
        AlertDialog.Builder dialog=new AlertDialog.Builder(context);
        dialog.setMessage("Supprimer la note");
        dialog.setTitle("Vous etes entrain de supprimer une note ");


        dialog.setPositiveButton("valider",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int which) {
                        DeleteNote(position);
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
    public void DeleteNote(int position) {
          String index = ListNotes.get(position).getId();
          //Toast.makeText(parentActivity.getApplicationContext(), "Note supprimée" + position, Toast.LENGTH_SHORT).show();
          firebaseDatabase.child(String.valueOf(index)).removeValue();
      }

}
