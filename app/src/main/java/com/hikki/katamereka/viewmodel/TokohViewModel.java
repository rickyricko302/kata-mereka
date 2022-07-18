package com.hikki.katamereka.viewmodel;

import android.app.Application;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.hikki.katamereka.model.TokohModel;
import com.hikki.katamereka.network.ApiService;
import com.hikki.katamereka.network.RetroInstance;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.util.List;

public class TokohViewModel extends AndroidViewModel {
    private MutableLiveData<List<TokohModel.Tokoh>> dataTokoh;
    private MutableLiveData<Integer> paging;
    private MutableLiveData<String> msg;
    private MutableLiveData<Boolean> hasNext = new MutableLiveData<>();
    private String param;
    public TokohViewModel(@NonNull Application application, String param) {
        super(application);
        this.param = param;
    }

    public void getTokoh(int page){
        ApiService api = new RetroInstance().getTwin().create(ApiService.class);
        api.getTokoh(param,page).enqueue(new Callback<TokohModel>() {
            @Override
            public void onResponse(Call<TokohModel> call, Response<TokohModel> response) {
                dataTokoh.setValue(response.body().getTokohList());
                hasNext.setValue(response.body().getNext());
               // Toast.makeText(getApplication().getApplicationContext(),response.body().getTokohList().toString(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<TokohModel> call, Throwable t) {
                dataTokoh.setValue(null);
                hasNext.setValue(null);
               // Toast.makeText(getApplication().getApplicationContext(), t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    public LiveData<List<TokohModel.Tokoh>> getDataTokoh() {
        if(dataTokoh == null){
            dataTokoh = new MutableLiveData<>();
            getTokoh(paging.getValue());
        }
        return dataTokoh;
    }
    public void setPage(int page){
        paging.setValue(page);
    }
    public boolean getHasNext(){

        return hasNext.getValue();
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
