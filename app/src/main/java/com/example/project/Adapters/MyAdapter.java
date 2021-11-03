package com.example.project.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project.Activites.home_page_activity;
import com.example.project.Model.Matiere;
import com.example.project.R;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.myViewHolder> {

    ArrayList<Matiere> mats;
    Context context;
    FirebaseDatabase firebaseDatabase;
    home_page_activity homePageActivity;
    Matiere matiere;


    public MyAdapter(Context context, ArrayList<Matiere> data) {
        this.context = context;
        this.mats = data;
        firebaseDatabase= FirebaseDatabase.getInstance();

    }
    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //Log.d("sizef", "getItemCount: "+ mats.size());
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.cardviewlist, parent, false);



        return new myViewHolder(view);


    }

    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, @SuppressLint("RecyclerView") int position) {

        if(mats.get(position)!=null) {
            holder.matName.setText(mats.get(position).getName());
            holder.coef.setText(String.valueOf(mats.get(position).getCoef()));
            System.out.println(mats.get(position).getTp());
            if(mats.get(position).getTp() == false) {
                holder.tp.setVisibility(View.GONE);
            }
            if(mats.get(position).getDc() == false) {
                holder.dc.setVisibility(View.INVISIBLE);
            }
            if(mats.get(position).getExam() == false) {
                holder.ds.setVisibility(View.INVISIBLE);
            }
        }
// partie delete subject
        holder.BtnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              long index = (long) mats.get(position).getId()+1;
                Toast.makeText(holder.matName.getContext(), "index", Toast.LENGTH_SHORT).show();
                firebaseDatabase.getReference().child("Etudiant").child(String.valueOf(index)).removeValue();
            }
        });

    }



    @Override
    public int getItemCount() {
        int x = mats.size();
        return x;
    }

    public class myViewHolder extends RecyclerView.ViewHolder {

        TextView tp, dc, ds, matName, coef ;
        ImageView BtnDelete;


        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            tp= itemView.findViewById(R.id.tp_txt);
            dc= itemView.findViewById(R.id.dc_txt);
            ds= itemView.findViewById(R.id.ds_txt);
            coef= itemView.findViewById(R.id.coef);
            matName= itemView.findViewById(R.id.matName);
            BtnDelete = itemView.findViewById(R.id.BtnDelete);

        }
    }
}
