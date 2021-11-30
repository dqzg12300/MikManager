package com.mik.mikmanager.ui.readme;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.mik.mikmanager.Common.ConfigUtil;
import com.mik.mikmanager.Common.FileHelper;
import com.mik.mikmanager.databinding.FragmentGlobalSettingBinding;
import com.mik.mikmanager.databinding.FragmentReadmeBinding;

public class ReadMeFragment extends Fragment {

    private ReadMeViewModel readMeViewModel;
    private FragmentReadmeBinding binding;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        readMeViewModel =
                new ViewModelProvider(this).get(ReadMeViewModel.class);

        binding = FragmentReadmeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}