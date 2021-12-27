package com.example.project.Fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.project.Activites.AddFileActivity;
import com.example.project.Activites.ListePhotoActivity;
import com.example.project.Activites.ViewFileActivity;
import com.example.project.Activites.listFilesActivity;
import com.example.project.R;

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
    private final int CHOOSE_PDF_FROM_DEVICE=1001;
    private final int RESULT_OK=-1;
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


        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_about, container, false);
        Button Photos = v.findViewById(R.id.BtnListePhotos);
        Button Files = v.findViewById(R.id.BtnListFichier);




        //Navigate to the add photo
        Photos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =new Intent(getActivity(), ListePhotoActivity.class);
                i.putExtra("matiere",mParam1);
                startActivity(i);
            }
        });
        //Navigate to the add file
        Files.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callChooseFileFromDevice();


            }
        });


        return v;

    }
    public void callChooseFileFromDevice(){
        Intent intent = new Intent (Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("application/pdf");
        startActivityForResult(intent,CHOOSE_PDF_FROM_DEVICE);

    }

    public void onActivityResult(int requestCode , int resultCode , Intent resultData) {
        super.onActivityResult(requestCode, resultCode, resultData);
        if(requestCode==CHOOSE_PDF_FROM_DEVICE && resultCode==RESULT_OK && resultData.getData()!=null){
             Uri data = resultData.getData();

            Intent i =new Intent(getActivity(), ViewFileActivity.class);
            i.putExtra("fileUri",data.toString());
            startActivity(i);
        }
    }



}