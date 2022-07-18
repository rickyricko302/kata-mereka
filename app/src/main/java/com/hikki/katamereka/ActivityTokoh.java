package com.hikki.katamereka;

import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.airbnb.lottie.LottieAnimationView;
import com.google.android.material.snackbar.Snackbar;
import com.hikki.katamereka.adapter.KategoriAdapter;
import com.hikki.katamereka.adapter.TokohAdapter;
import com.hikki.katamereka.databinding.ActivityTokohBinding;
import com.hikki.katamereka.model.TokohModel;
import com.hikki.katamereka.viewmodel.MyViewModelFactory;
import com.hikki.katamereka.viewmodel.TokohViewModel;

import java.util.Collections;
import java.util.List;

import static java.util.Collections.*;

public class ActivityTokoh extends AppCompatActivity {

    ActivityTokohBinding binding;
    RecyclerView recyclerView;
    SearchView sv;
    TokohAdapter adapter;
    LottieAnimationView lottie;
    TokohViewModel viewModel;
    Button textLoad,nextPage,backPage;
    String yahaha;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_tokoh);
        recyclerView = binding.recyclerTokoh;
        sv = binding.searchViewTokoh;
        lottie = binding.lottieTokoh;
        textLoad = binding.tokohLoadText;
        sv = binding.searchViewTokoh;
        nextPage = binding.nextPage;
        backPage = binding.backPage;
        yahaha = getIntent().getStringExtra("key");
        setUp();
        viewModel = new ViewModelProvider(this, new MyViewModelFactory(getApplication(),yahaha)).get(TokohViewModel.class);
        binding.setLifecycleOwner(this);
        binding.tokohAtas.setText(yahaha.replace("-"," ").replace("_"," ")+"\n Page "+viewModel.getPaging());
        viewModel.getDataTokoh().observe(this, new Observer<List<TokohModel.Tokoh>>() {
            @Override
            public void onChanged(List<TokohModel.Tokoh> tokohs) {
                lottie.setVisibility(View.GONE);
                textLoad.setText("Memuat Data...");
                textLoad.setVisibility(View.VISIBLE);
                if(tokohs != null) {
                  //  Toast.makeText(ActivityTokoh.this, String.valueOf(tokohs.size()), Toast.LENGTH_SHORT).show();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            textLoad.setVisibility(View.GONE);
                            adapter.setData(tokohs);
                            recyclerView.setVisibility(View.VISIBLE);
                            if(!sv.getQuery().toString().isEmpty()) {
                               adapter.getFilter().filter(sv.getQuery().toString());
                            }
                            if(viewModel.getHasNext()){
                                nextPage.setVisibility(View.VISIBLE);
                                nextPage.setText("Page "+String.valueOf(viewModel.getPaging()+1));
                            }
                            else {
                                nextPage.setVisibility(View.GONE);
                            }
                            if(viewModel.getPaging()>1){
                                backPage.setVisibility(View.VISIBLE);
                                backPage.setText("Page "+String.valueOf(viewModel.getPaging()-1));
                            }
                            else {
                                backPage.setVisibility(View.GONE);
                            }
                              adapter.setClick(new TokohAdapter.onItemClick() {
                                @Override
                                public void onClick(View v, int position) {
                                 //   Toast.makeText(getApplicationContext(), adapter.getTokoh().get(position).getNama(), Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(ActivityTokoh.this,ActivityQuotes.class);
                                    intent.putExtra("key",adapter.getTokoh().get(position).getNama());
                                    startActivity(intent);
                                }
                            });

                        }
                    },3000);
                }
                else{
                    Toast.makeText(ActivityTokoh.this, "Koneksi Error", Toast.LENGTH_SHORT).show();
                    textLoad.setText("Coba Lagi");
                }
            }
        });
        nextPage.setOnClickListener(v->{
            sv.setQuery("",false);
            sv.clearFocus();
            viewModel.setPage(viewModel.getPaging()+1);
            viewModel.getTokoh(viewModel.getPaging());
            binding.tokohAtas.setText(yahaha.replace("-"," ").replace("_"," ")+"\n Page "+viewModel.getPaging());
            recyclerView.setVisibility(View.GONE);
            lottie.setVisibility(View.VISIBLE);
            nextPage.setVisibility(View.GONE);
            backPage.setVisibility(View.GONE);

        });
        backPage.setOnClickListener(v->{
            sv.setQuery("",false);
            sv.clearFocus();
            viewModel.setPage(viewModel.getPaging()-1);
            viewModel.getTokoh(viewModel.getPaging());
            binding.tokohAtas.setText(yahaha.replace("-"," ").replace("_"," ")+"\n Page "+viewModel.getPaging());
            recyclerView.setVisibility(View.GONE);
            lottie.setVisibility(View.VISIBLE);
            nextPage.setVisibility(View.GONE);
            backPage.setVisibility(View.GONE);

        });
        binding.closeTokoh.setOnClickListener(v->{
            binding.closeTokoh.setVisibility(View.GONE);
            finish();
        });

        textLoad.setOnClickListener(v->{
            if(textLoad.getText().toString().equals("Coba Lagi")){
                lottie.setVisibility(View.VISIBLE);
                textLoad.setVisibility(View.GONE);
                viewModel.getTokoh(viewModel.getPaging());
            }

        });
        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                adapter.getFilter().filter(s);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                if(s.isEmpty()){
                    adapter.getFilter().filter(s);
                }
                return false;
            }
        });
    }
    public void setUp(){
        int id = sv.getContext().getResources().getIdentifier("android:id/search_src_text",null,null);
        TextView textView = (TextView) findViewById(id);
        textView.setTextColor(Color.BLACK);
        textView.setTextSize(15);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        adapter = new TokohAdapter();
        recyclerView.setAdapter(adapter);

    }

    @Override
    protected void onResume() {
        super.onResume();
        sv.clearFocus();
        binding.roottTokoh.requestFocus();
    }
}