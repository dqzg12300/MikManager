package com.mik.mikmanager.ui.addpackage;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.mik.mikmanager.Common.FragmentListen;
import com.mik.mikmanager.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link WorkAppFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WorkAppFragment extends Fragment {

    public Button btnSelectPackage;

    public EditText txtAppName;

    public EditText txtPackageName;

    public Button btnSave;

    public Button btnRemove;

    private FragmentListen listener;

    public WorkAppFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Activity activity) {
        // TODO Auto-generated method stub
        super.onAttach(activity);
        listener=(FragmentListen) activity;
    }

    public static WorkAppFragment newInstance() {
        WorkAppFragment fragment = new WorkAppFragment();
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_work_app, container, false);
    }

    @Override
    public void onStart() {
        super.onStart();
        txtPackageName = (EditText)getActivity().findViewById(R.id.txtPackageName);
        txtAppName = (EditText)getActivity().findViewById(R.id.txtAppName);
        btnSave =  (Button)getActivity().findViewById(R.id.btnSave);
        btnRemove = (Button)getActivity().findViewById(R.id.btnRemove);
        btnSelectPackage = (Button)getActivity().findViewById(R.id.btnSelectPackage);
        listener.onWorkAppAttach();
    }

}
