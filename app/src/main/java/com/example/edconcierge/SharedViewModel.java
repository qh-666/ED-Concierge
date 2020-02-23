package com.example.edconcierge;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SharedViewModel extends ViewModel {
    private final MutableLiveData<Integer> indexHospital = new MutableLiveData<Integer>();

    public void indexHospital(int i) {
        indexHospital.setValue(i);
    }

    public LiveData<Integer> getIndexHospital() {
        return indexHospital;
    }
}
