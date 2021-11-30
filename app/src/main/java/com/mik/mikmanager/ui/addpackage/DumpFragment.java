package com.mik.mikmanager.ui.addpackage;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;

import com.mik.mikmanager.Common.FragmentListen;
import com.mik.mikmanager.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DumpFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DumpFragment extends Fragment {


    public EditText txtBreakClass;
    public EditText txtWhiteClass;
    public Button btnSelectWhitePath;
    public EditText txtWhitePath;
    public Switch swTuoke;
    public Switch swDeep;
    private FragmentListen listener;

    private static final String KEY = "title";

    public DumpFragment() {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Activity activity) {
        // TODO Auto-generated method stub
        super.onAttach(activity);
        listener=(FragmentListen) activity;
    }

    public DumpFragment(String title) {
        Bundle b = new Bundle();
        b.putString("key", title);
        setArguments(b);
        // Required empty public constructor
    }

    public static DumpFragment newInstance(String title) {
        DumpFragment fragment = new DumpFragment();
        Bundle bundle = new Bundle();
        bundle.putString(KEY,title);
        fragment.setArguments(bundle);
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
        return inflater.inflate(R.layout.fragment_dump, container, false);
    }
    @Override
    public void onStart() {
        super.onStart();
        txtBreakClass = (EditText)getView().findViewById(R.id.txtBreakClass);
        txtWhiteClass=(EditText)getView().findViewById(R.id.txtWhiteClass);
        btnSelectWhitePath=(Button)getView().findViewById(R.id.btnSelectWhitePath);
        txtWhitePath=(EditText)getView().findViewById(R.id.txtWhitePath);
        swTuoke = (Switch)getView().findViewById(R.id.swTuoke);
        swDeep = (Switch)getView().findViewById(R.id.swDeep);
        listener.onDumpAttach();
    }
}