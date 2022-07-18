package com.hikki.katamereka.network;

import com.hikki.katamereka.model.CariKataModel;
import com.hikki.katamereka.model.QuoteModel;
import com.hikki.katamereka.model.TokohModel;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

import java.util.ArrayList;
import java.util.List;

public interface ApiService {

    @GET("api/v1/kata-bijak/kategori")
    Call<ArrayList<String>> getKategori();

    @GET("api/v1/kata-bijak/kategori/tokoh")
    Call<TokohModel> getTokoh(@Query("q") String key, @Query("page") int page);

    @GET("api/v1/kata-bijak/kata/tokoh")
    Call<QuoteModel> getQuoteTokoh(@Query("q") String key, @Query("page") int page);

    @GET("search/yahaha/{kata}/{page}")
    Call<CariKataModel> getCariKata(@Path("kata") String kata,@Path("page") int page);
}
