package com.hikki.katamereka.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.hikki.katamereka.adapter.KategoriAdapter;
import com.hikki.katamereka.network.ApiService;
import com.hikki.katamereka.network.RetroInstance;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.ArrayList;
import java.util.List;

public class HomeViewModel extends ViewModel {

    private MutableLiveData<ArrayList<String>> dataKategori = new MutableLiveData<>();
    public void reqKategori(){
        ApiService api = new RetroInstance().getTwin().create(ApiService.class);
        api.getKategori().enqueue(new Callback<ArrayList<String>>() {
            @Override
            public void onResponse(Call<ArrayList<String>> call, Response<ArrayList<String>> response) {
              dataKategori.setValue(response.body());
            }
            @Override
            public void onFailure(Call<ArrayList<String>> call, Throwable t) {
                dataKategori.setValue(null);
            }
        });
    }

    public LiveData<ArrayList<String>> getDataKategori() {
        if(dataKategori.getValue() == null){
            reqKategori();
        }
        return dataKategori;
    }

}
