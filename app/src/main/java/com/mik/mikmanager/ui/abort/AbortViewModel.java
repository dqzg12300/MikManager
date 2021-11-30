package com.mik.mikmanager.ui.abort;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class AbortViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public AbortViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is readme fragment");
    }
    public LiveData<String> getText() {
        return mText;
    }
}