package com.mik.mikmanager.ui.addpackage;

import android.app.Activity;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Switch;

import com.mik.mikmanager.Common.FragmentListen;
import com.mik.mikmanager.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RomLogFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RomLogFragment extends Fragment {

    public Switch swInvokePrint;

    public Switch swRegisterNativePrint;

    public Switch swJNIMethodPrint;

    private FragmentListen listener;

    public RomLogFragment() {
        // Required empty public constructor
    }
    public static RomLogFragment newInstance() {
        RomLogFragment fragment = new RomLogFragment();
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
        return inflater.inflate(R.layout.fragment_rom_log, container, false);
    }
    @Override
    public void onStart() {

        super.onStart();
        swInvokePrint = (Switch)getActivity().findViewById(R.id.swInvokePrint);
        swRegisterNativePrint = (Switch)getActivity().findViewById(R.id.swRegisterNativePrint);
        swJNIMethodPrint = (Switch)getActivity().findViewById(R.id.swJNIMethodPrint);
//        swFileLog=(Switch)getActivity().findViewById(R.id.swFileLog);
        listener.onRomLogAttach();
    }
}