package com.hikki.katamereka.viewmodel;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.hikki.katamereka.model.QuoteModel;
import com.hikki.katamereka.network.ApiService;
import com.hikki.katamereka.network.RetroInstance;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.List;

public class QuotesViewModel extends AndroidViewModel {
    private String param;
    private MutableLiveData<Boolean> hasNext = new MutableLiveData<>();
    private MutableLiveData<Integer> paging;
    private MutableLiveData<QuoteModel> data;

    public QuotesViewModel(@NonNull Application application, String param) {
        super(application);
        this.param = param;
    }

    public void getQuoteTokoh(int page){
        ApiService api = new RetroInstance().getTwin().create(ApiService.class);
        api.getQuoteTokoh(param,page).enqueue(new Callback<QuoteModel>() {
            @Override
            public void onResponse(Call<QuoteModel> call, Response<QuoteModel> response) {
                hasNext.setValue(response.body().getHasNext());
                data.setValue(response.body());

            }

            @Override
            public void onFailure(Call<QuoteModel> call, Throwable t) {
                data.setValue(null);
                hasNext.setValue(null);
            }
        });
    }

    public LiveData<QuoteModel> getData(){
        if(data == null){
            data = new MutableLiveData<>();
            getQuoteTokoh(getPaging());
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
