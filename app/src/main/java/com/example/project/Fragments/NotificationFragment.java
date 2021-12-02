package com.example.project.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.project.Activites.Upload_Notepad_Matiere;
import com.example.project.Activites.Upload_image_Activity;
import com.example.project.R;

import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

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

    // TODO: Rename and change types of parameters
    private String mParam1;
    //private String mParam2;

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

        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_notification, container, false);
        FloatingActionButton addNotepad = v.findViewById(R.id.AddNotepad);
        //Navigate to the add photo
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
}