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
 * Use the {@link RomInjectFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RomInjectFragment extends Fragment {

    public Button btnSelectSo;
    public Button btnClearSo;
    public TextView txtSoList;
    public Button btnSelectDex;
    public Button btnClearDex;
    public TextView txtDexList;
    public Switch swInjectDobby;
    public EditText txtDexClassName;

    private FragmentListen listener;

    public RomInjectFragment() {
        // Required empty public constructor
    }
    public static RomInjectFragment newInstance() {
        RomInjectFragment fragment = new RomInjectFragment();
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
        return inflater.inflate(R.layout.fragment_rom_inject, container, false);
    }
    @Override
    public void onStart() {
        super.onStart();
        btnSelectSo=(Button)getActivity().findViewById(R.id.btnSelectSo);
        btnClearSo=(Button)getActivity().findViewById(R.id.btnClearSo);
        btnSelectDex=(Button)getActivity().findViewById(R.id.btnSelectDex);
        btnClearDex=(Button)getActivity().findViewById(R.id.btnClearDex);
        txtSoList=(TextView)getActivity().findViewById(R.id.txtSoPath);
        txtDexList=(TextView)getActivity().findViewById(R.id.txtDexPath);
        swInjectDobby=(Switch)getActivity().findViewById(R.id.swInjectDobby);
        txtDexClassName=(EditText) getActivity().findViewById(R.id.txtDexClassName);
        listener.onRomInjectAttach();
    }
}