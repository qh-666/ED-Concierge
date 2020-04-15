package com.example.edconcierge;

import android.graphics.Bitmap;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

public class SharedViewModel extends ViewModel {
    private final MutableLiveData<String> hospitalName = new MutableLiveData<>();
    private final MutableLiveData<List<Bitmap>> informationIcons = new MutableLiveData<>();
    private final MutableLiveData<List<String>> informationQuestions = new MutableLiveData<>();
    private final MutableLiveData<List<String>> informationAnswers = new MutableLiveData<>();

    public void hospitalName(String string) {
        hospitalName.setValue(string);
    }

    public LiveData<String> getHospitalName() {
        return hospitalName;
    }

}
