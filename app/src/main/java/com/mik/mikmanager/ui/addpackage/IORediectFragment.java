package com.mik.mikmanager.ui.addpackage;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.mik.mikmanager.Common.FragmentListen;
import com.mik.mikmanager.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link IORediectFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class IORediectFragment extends Fragment {


    public EditText txtForbids;
    public EditText txtRediectFile;
    public EditText txtRediectDir;

    private FragmentListen listener;

    public IORediectFragment() {
        // Required empty public constructor
    }
    public static IORediectFragment newInstance() {
        IORediectFragment fragment = new IORediectFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onAttach(Activity activity) {
        // TODO Auto-generated method stub
        super.onAttach(activity);
        listener=(FragmentListen) activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_iorediect, container, false);
    }
    @Override
    public void onStart() {
        super.onStart();
        txtForbids=(EditText) getActivity().findViewById(R.id.txtForbids);
        txtRediectDir=(EditText) getActivity().findViewById(R.id.txtRediectDir);
        txtRediectFile=(EditText) getActivity().findViewById(R.id.txtRediectFile);
        listener.onIORediectAttach();
    }
}