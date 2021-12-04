package com.example.project.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.project.Model.Image;
import com.example.project.R;

import java.util.ArrayList;

public class ImageAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<String> titre;
    private LayoutInflater layoutInflater;
    private ArrayList<Image> Images;

    public ImageAdapter(Context context, ArrayList<String> titre, ArrayList<Image> images) {
        this.context = context;
        this.titre = titre;
        this.layoutInflater = layoutInflater;
        Images = images;
    }

    @Override
    public int getCount() {
        return titre.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @SuppressLint("ServiceCast")
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(layoutInflater==null)
        {
            layoutInflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        }
        View RootView = layoutInflater.inflate(R.layout.image_card, parent, false);

        ImageView image =RootView.findViewById(R.id.Image);
        TextView  desc= RootView.findViewById(R.id.description);
        Image imagess= Images.get(position);
        byte[] ima = imagess.getImage();

            image.setImageBitmap(BitmapFactory.decodeByteArray(ima, 0, ima.length));
            desc.setText(titre.get(position));




        return RootView;
    }
}
