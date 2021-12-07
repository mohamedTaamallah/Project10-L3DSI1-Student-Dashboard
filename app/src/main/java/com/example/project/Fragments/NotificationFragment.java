package com.example.project.Fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.project.Activites.EditNotePadActivity;
import com.example.project.Activites.Upload_Notepad_Matiere;
import com.example.project.Activites.Upload_image_Activity;
import com.example.project.Adapters.ImageAdapter;
import com.example.project.Adapters.MyContextApp;
import com.example.project.Adapters.NotepadAdapter;
import com.example.project.Model.Image;
import com.example.project.Model.Notepad;
import com.example.project.R;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.project.SQL_lite.DataBaseHandler;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NotificationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NotificationFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    //private static final String ARG_PARAM2 = "param2";
    NotepadAdapter.RecycleViewClickListener clickListener;

    // TODO: Rename and change types of parameters
    private String mParam1;
    //private String mParam2;
    ArrayList <Notepad> list_descritption;
    DatabaseReference mDataRef;
    public NotificationFragment(String matiere_id) {
        // Required empty public constructor
        mParam1=matiere_id;
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     *
     * @return A new instance of fragment NotificationFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static NotificationFragment newInstance(String param1) {
        NotificationFragment fragment = new NotificationFragment(param1);
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        //args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            //mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);

        //DataBaseHandler db = new DataBaseHandler(getActivity()); -- firebase

        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_notification, container, false);
        FloatingActionButton addNotepad = v.findViewById(R.id.AddNotepad);
        RecyclerView gridView = v.findViewById(R.id.ListNotepad);

        LinearLayoutManager layout = new LinearLayoutManager(getActivity());
        gridView.setLayoutManager(layout);
        registerForContextMenu(gridView);

        //ImageView delete = v.findViewById(R.id.supprimer);

        //NotepadAdapter notepadAdapter =afficher(gridView);
        afficher(gridView);
        setOnclickListener();

        //Navigate to the add note
        addNotepad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =new Intent(getActivity(), Upload_Notepad_Matiere.class);
                i.putExtra("matiere",mParam1);
                startActivity(i);
            }
        });

        return v;

    }
    MyContextApp app;
    //public NotepadAdapter afficher (--Firebase db, RecyclerView gridView ){
    public void afficher (RecyclerView gridView ){

        app = (MyContextApp) getActivity().getApplicationContext();
        System.out.println(app.getMatiere());
        mDataRef = FirebaseDatabase.getInstance().getReference("Etudiant").child(app.getUid()).child("Matiere").child(app.getMatiere().getId()).child("Notepad");

        list_descritption  = new ArrayList <>();


        mDataRef.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list_descritption.clear();

                for (DataSnapshot postSnapshot : snapshot.getChildren()){
                    Notepad not = postSnapshot.getValue(Notepad.class);
                    not.setId(postSnapshot.getKey());
                    list_descritption.add(not);
                }
                NotepadAdapter notepadAdapter = new NotepadAdapter(app,list_descritption,getActivity(), mDataRef,clickListener);
                gridView.setAdapter(notepadAdapter);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //return notepadAdapter;
    }


    private void setOnclickListener() {
        clickListener = new NotepadAdapter.RecycleViewClickListener() {
            @Override
            public void onClick(View v, int position) {
                System.out.println(list_descritption.get(position).getTexte());
                Intent i = new Intent(getActivity().getApplicationContext(), EditNotePadActivity.class);

                app = (MyContextApp) getActivity().getApplicationContext();
                app.setNotepad(list_descritption.get(position));

                startActivity(i);

            }
        };
    }


}