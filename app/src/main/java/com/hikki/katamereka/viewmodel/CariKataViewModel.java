package com.hikki.katamereka.viewmodel;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.hikki.katamereka.model.CariKataModel;
import com.hikki.katamereka.network.ApiService;
import com.hikki.katamereka.network.RetroInstance;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CariKataViewModel extends AndroidViewModel {
    private MutableLiveData<CariKataModel> data;
    private String param;
    private MutableLiveData<Boolean> hasNext = new MutableLiveData<>();
    private MutableLiveData<Integer> paging;
    private Application app;
    public CariKataViewModel(@NonNull Application application, String param) {
        super(application);
        this.param = param;
        this.app = application;
    }
    public void getCariKata(int page){
        ApiService api = new RetroInstance().getFlaski().create(ApiService.class);
        api.getCariKata("kata-"+param.replace(" ","+"),page).enqueue(new Callback<CariKataModel>() {
            @Override
            public void onResponse(Call<CariKataModel> call, Response<CariKataModel> response) {
                data.postValue(response.body());
                hasNext.setValue(response.body().getHasNext());
            }

            @Override
            public void onFailure(Call<CariKataModel> call, Throwable t) {
                if(t.getMessage().contains("Expected BEGIN_ARRAY")){
                    CariKataModel mo = new CariKataModel();
                    mo.setStatus("kata tidak ditemukan");
                    data.postValue(mo);
                    hasNext.setValue(null);
                }
                else {
                    data.postValue(null);
                    hasNext.setValue(null);
                }
            }
        });
    }

    public LiveData<CariKataModel> getData(){
        if(data == null){
            data = new MutableLiveData<>();
            getCariKata(getPaging());
        }
        return data;
    }
    public boolean getHasNexts(){
        return hasNext.getValue();
    }

    public void setPage(int page){
        paging.setValue(page);
    }

    public int getPaging(){
        if(paging == null){
            paging = new MutableLiveData<>();
            paging.setValue(1);
        }
        if(paging.getValue() !=null) {
            return paging.getValue();
        }
        return 1;
    }
}
