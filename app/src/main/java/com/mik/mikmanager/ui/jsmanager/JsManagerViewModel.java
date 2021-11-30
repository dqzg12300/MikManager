package com.mik.mikmanager.ui.jsmanager;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class JsManagerViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public JsManagerViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is slideshow fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}