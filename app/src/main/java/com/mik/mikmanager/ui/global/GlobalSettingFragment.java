package com.mik.mikmanager.ui.global;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.RemoteException;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.mik.mikmanager.Common.ConfigUtil;
import com.mik.mikmanager.Common.FileHelper;
import com.mik.mikmanager.Common.ServiceUtils;
import com.mik.mikmanager.databinding.FragmentGlobalSettingBinding;

public class GlobalSettingFragment extends Fragment {

    private GlobalSettingViewModel globalSettingViewModel;
    private FragmentGlobalSettingBinding binding;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        globalSettingViewModel =
                new ViewModelProvider(this).get(GlobalSettingViewModel.class);

        binding = FragmentGlobalSettingBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        initUi();
        binding.btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ConfigUtil.sysHide=binding.swSysHide.isChecked();
                FileHelper.writeTxtToFile(binding.txtBreakClass.getText().toString(),"/dev/mikrom/config/breakClass.config");
                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(binding.getRoot().getContext());
                SharedPreferences.Editor editor = sharedPreferences.edit();
                String sysHide=binding.swSysHide.isChecked()?"1":"0";
                editor.putString("sysHide", sysHide);
                editor.apply();
            }
        });
        return root;
    }

    public void initUi(){
        String res="";
        try {
            res=ServiceUtils.getiMikRom().readFile(ConfigUtil.breakConfigPath);
        } catch (RemoteException e) {
            Log.e(ConfigUtil.TAG,e.getMessage());
        }
        if(res!=null&&res.length()>0){
            binding.txtBreakClass.setText(res);
        }
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(binding.getRoot().getContext());
        String sysHide = sharedPreferences.getString("sysHide", "");
        if(sysHide.equals("0")){
            binding.swSysHide.setChecked(false);
        }else{
            binding.swSysHide.setChecked(true);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}