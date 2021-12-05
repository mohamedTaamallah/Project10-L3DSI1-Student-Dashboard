package com.example.project.Fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.example.project.Activites.MatiereDetailsActivity;
import com.example.project.Activites.home_page_activity;
import com.example.project.Activites.liste_important_dates;
import com.example.project.Adapters.ImageAdapter;
import com.example.project.Adapters.MyAdapter;
import com.example.project.Model.Image;
import com.example.project.R;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project.Activites.Upload_image_Activity;
import com.example.project.SQL_lite.DataBaseHandler;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AboutFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AboutFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    ImageAdapter.RecycleViewClickListener clickListener;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AboutFragment(String matiere_id) {
        // Required empty public constructor
        mParam1=matiere_id;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @return A new instance of fragment AboutFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AboutFragment newInstance(String param1) {
        AboutFragment fragment = new AboutFragment(param1);
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        DataBaseHandler db = new DataBaseHandler(getActivity());


        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_about, container, false);
        FloatingActionButton addPhoto = v.findViewById(R.id.AddPhoto);
        RecyclerView gridView = v.findViewById(R.id.Grid);

        LinearLayoutManager layout = new LinearLayoutManager(getActivity());
        gridView.setLayoutManager(layout);
        registerForContextMenu(gridView);

        ImageView delete = v.findViewById(R.id.delete);
         ImageAdapter imageAdapter =afficher(db,gridView);
        setOnclickListener();

        //Navigate to the add photo
        addPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =new Intent(getActivity(), Upload_image_Activity.class);
                i.putExtra("matiere",mParam1);
                startActivity(i);
            }
        });


        return v;

    }


    public ImageAdapter afficher (DataBaseHandler db,RecyclerView gridView ){
    ArrayList <Image> list_image  = db.getAllImage(mParam1);
    ArrayList <String> list_descritption  = new ArrayList <>();
    ArrayList <byte[]> list_images  = new ArrayList <>();

    for (int i = 0; i <list_image.size() ; i++) {
        list_descritption.add(list_image.get(i).getTitre());
    }
    //Toast.makeText(getActivity(), list_images.size()+ "Test  ", Toast.LENGTH_SHORT).show();
    ImageAdapter imageAdapter = new ImageAdapter(getActivity(),list_descritption,list_image,getActivity(),clickListener);
    gridView.setAdapter(imageAdapter);
    return imageAdapter;
}

    private void setOnclickListener() {
        clickListener = new ImageAdapter.RecycleViewClickListener() {
            @Override
            public void onClick(View v, int position) {
               Log.d("testing","testing");
            }
        };
    }




}