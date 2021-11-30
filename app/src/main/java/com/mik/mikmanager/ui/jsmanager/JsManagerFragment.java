package com.mik.mikmanager.ui.jsmanager;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.mik.mikmanager.databinding.FragmentJsmanagerBinding;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class JsManagerFragment extends Fragment {

    private JsManagerViewModel jsManagerViewModel;
    private FragmentJsmanagerBinding binding;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        jsManagerViewModel =
                new ViewModelProvider(this).get(JsManagerViewModel.class);

        binding = FragmentJsmanagerBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textSlideshow;
        textView.setText("todo 待开发");
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}