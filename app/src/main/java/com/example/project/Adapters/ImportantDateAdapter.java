package com.example.project.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project.Model.date;
import com.example.project.R;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

public class ImportantDateAdapter extends RecyclerView.Adapter<ImportantDateAdapter.MyviewHolder1>{
    ArrayList <date> list ;
    Context context;
    DatabaseReference firebaseDatabase;

    public ImportantDateAdapter(Context c , ArrayList<date> l, DatabaseReference database) {
        this.context=c;
        this.list=l;
        this.firebaseDatabase=database;


    }

    @NonNull
    @Override
    public MyviewHolder1 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(this.context);
        View view = layoutInflater.inflate(R.layout.date_card,parent,false);
        return new MyviewHolder1(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyviewHolder1 holder,@SuppressLint("RecyclerView") int position) {

            holder.desc.setText(list.get(position).getDescription());
            holder.hor.setText(list.get(position).getTime());
            holder.day.setText(list.get(position).getDate());
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String index = list.get(position).getId();
                Toast.makeText(holder.desc.getContext(), "Evenement supprimée", Toast.LENGTH_SHORT).show();
                firebaseDatabase.child(index).removeValue();
            }
        });





    }

    @Override
    public int getItemCount() {
        return list.size(); }

    public class MyviewHolder1 extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener{
        TextView desc,hor,day;
        ImageView delete;
        ;

        public MyviewHolder1(@NonNull View itemView) {
            super(itemView);
            desc = itemView.findViewById(R.id.description);
            hor =itemView.findViewById(R.id.horaire);
            day = itemView.findViewById(R.id.date);
            delete =itemView.findViewById(R.id.delete);
        }


        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            int test = this.getAdapterPosition();
            menu.add(this.getAdapterPosition(),test,0,"Go to Wikipedia ");
            menu.add(this.getAdapterPosition(),test,0,"delete");
        }
    }
}

