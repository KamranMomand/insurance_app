package com.example.projectinsurance.ui.slideshow;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SlideshowViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public SlideshowViewModel() {
        mText = new MutableLiveData<>();
//        mText.setValue("This is Claim fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}