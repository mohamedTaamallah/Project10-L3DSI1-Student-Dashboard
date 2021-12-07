package com.example.project.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.project.Activites.AddMatierActivity;
import com.example.project.Activites.EditNotesActivity;
import com.example.project.Activites.home_page_activity;
import com.example.project.Adapters.MyContextApp;
import com.example.project.Model.Matiere;
import com.example.project.R;

import org.w3c.dom.Text;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    TextView studentName;
    MyContextApp app;
    Matiere matiere;

    TextView noteDC, noteTP, noteDS;
    public HomeFragment() {
        // Required empty public constructor
        matiere = new Matiere();
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
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

        app = (MyContextApp) getActivity().getApplicationContext();
        matiere = app.getMatiere();

        OnBackPressedCallback callback = new OnBackPressedCallback(true /* enabled by default */) {
            @Override
            public void handleOnBackPressed() {
                startActivity(new Intent(getActivity().getApplicationContext(), home_page_activity.class));
            }
        };
        requireActivity().getOnBackPressedDispatcher().addCallback(this, callback);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        System.out.println("Matiere A modifier: "  +app.getMatiere());
        changeMatierName(matiere.getName());
        TextView EditNote = (TextView) getView().findViewById(R.id.EditNote);
        noteDC = (TextView) getView().findViewById(R.id.noteDC);
        noteTP = (TextView) getView().findViewById(R.id.noteTP);
        noteDS = (TextView) getView().findViewById(R.id.noteDS);

        noteDC.setText(String.valueOf(app.getMatiere().getDc().getNote()));
        noteTP.setText(String.valueOf(app.getMatiere().getTp().getNote()));
        noteDS.setText(String.valueOf(app.getMatiere().getExam().getNote()));

        checkVisibility();

        EditNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity().getApplicationContext(), EditNotesActivity.class));
            }
        });
    }


    public void changeMatierName(String name) {
        studentName = (TextView)getView().findViewById(R.id.StudentName);
        studentName.setText(name);
    }

    void checkVisibility() {
        if(!matiere.getExam().isExist()) {
            ((TextView)getView().findViewById(R.id.txtViewDS)).setVisibility(View.GONE);
            noteDS.setVisibility(View.GONE);
        }
        if(!matiere.getDc().isExist()) {
            ((TextView)getView().findViewById(R.id.txtViewDC)).setVisibility(View.GONE);
            noteDC.setVisibility(View.GONE);
        }
        if(!matiere.getTp().isExist()) {
            ((TextView)getView().findViewById(R.id.txtViewTP)).setVisibility(View.GONE);
            noteTP.setVisibility(View.GONE);
        }
    }
}