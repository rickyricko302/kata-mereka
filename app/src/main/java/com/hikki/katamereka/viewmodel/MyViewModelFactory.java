package com.hikki.katamereka.viewmodel;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class MyViewModelFactory implements ViewModelProvider.Factory {
    private Application mApplication;
    private String param;

    public MyViewModelFactory(Application mApplication, String param) {
        this.mApplication = mApplication;
        this.param = param;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if(modelClass == TokohViewModel.class) {
            return (T) new TokohViewModel(mApplication, param);
        }
        else if(modelClass == QuotesViewModel.class){
            return (T) new QuotesViewModel(mApplication,param);
        }
        else if(modelClass == CariKataViewModel.class){
            return (T) new CariKataViewModel(mApplication,param);
        }
        return null;
    }


}
