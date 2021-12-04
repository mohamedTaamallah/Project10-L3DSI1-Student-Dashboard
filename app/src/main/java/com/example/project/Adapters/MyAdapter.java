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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.myViewHolder> {

    ArrayList<Matiere> mats;
    Context context;
    DatabaseReference firebaseDatabase;
    home_page_activity homePageActivity;
    Matiere matiere;
    MyContextApp appContext;

    private RecycleViewClickListener clickListener;
    private  RecyclerViewLongClick longClickListener;


    public MyAdapter(Context context, ArrayList<Matiere> data, RecycleViewClickListener clickListener, RecyclerViewLongClick longClickListener, DatabaseReference firebaseDatabase) {
        this.context = context;
        this.mats = data;

        this.firebaseDatabase = firebaseDatabase;
        this.clickListener = clickListener;
        this.longClickListener = longClickListener;

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
            if(mats.get(position).getTp().isExist() == false) {
                holder.tp.setVisibility(View.INVISIBLE);
            }
            if(mats.get(position).getDc().isExist() == false) {
                holder.dc.setVisibility(View.INVISIBLE);
            }
            if(mats.get(position).getExam().isExist() == false) {
                holder.ds.setVisibility(View.INVISIBLE);
            }
        }
// partie delete subject
        holder.BtnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String index = mats.get(position).getId();
                Toast.makeText(holder.matName.getContext(), "Matiere supprimée", Toast.LENGTH_SHORT).show();
                firebaseDatabase.child(index).removeValue();
            }
        });

    }



    @Override
    public int getItemCount() {
        return mats.size();
    }

    public class myViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener, View.OnClickListener{

        TextView tp, dc, ds, matName, coef ;
        ImageView BtnDelete;


        public myViewHolder(@NonNull View itemView) {
            super(itemView);

            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);

            tp= itemView.findViewById(R.id.tp_txt);
            dc= itemView.findViewById(R.id.dc_txt);
            ds= itemView.findViewById(R.id.ds_txt);
            coef= itemView.findViewById(R.id.coef);
            matName= itemView.findViewById(R.id.matName);
            BtnDelete = itemView.findViewById(R.id.BtnDelete);

        }
        @Override
        public void onClick(View view) {
            clickListener.onClick(itemView, getAdapterPosition());
        }


        @Override
        public boolean onLongClick(View view) {
            longClickListener.onItemLongClicked(itemView, getAdapterPosition());
            return true;
        }
    }
    public interface RecycleViewClickListener {
        void onClick(View v, int positon);
    }
    public interface RecyclerViewLongClick {
        boolean onItemLongClicked(View v, int position);
    }
}
