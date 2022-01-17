package com.example.project.Adapters;

import android.app.Activity;
import android.content.Context;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project.Model.file;
import com.example.project.R;

import java.util.ArrayList;

public class FileAdapter extends RecyclerView.Adapter<FileAdapter.MyviewHolder3>{

    private Context context;
    private ArrayList<file> listFile;
    private Activity parentActivity;

    public FileAdapter(Context context , ArrayList<file> listFile, Activity parentActivity)
    {
        this.parentActivity=parentActivity;
        this.listFile=listFile;
        this.context=context;
    }


    @NonNull
    @Override
    public FileAdapter.MyviewHolder3 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(this.context);
        View view = layoutInflater.inflate(R.layout.image_card,parent,false);
        return new FileAdapter.MyviewHolder3(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FileAdapter.MyviewHolder3 holder, int position) {
        holder.desc.setText(listFile.get(position).getFileTitle());

    }

    @Override
    public int getItemCount() {
        return listFile.size();
    }

    public class MyviewHolder3 extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {
        TextView desc;

        public MyviewHolder3(@NonNull View itemView) {
            super(itemView);
            desc = itemView.findViewById(R.id.description);
            itemView.setOnCreateContextMenuListener(this);
        }


        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            int test = this.getAdapterPosition();
            menu.add(this.getAdapterPosition(),test,0,"View file ");
            menu.add(this.getAdapterPosition(),test,0,"delete");
            notifyDataSetChanged();
        }

    }
}
