package com.mik.mikmanager.ui.readme;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ReadMeViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public ReadMeViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is readme fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}