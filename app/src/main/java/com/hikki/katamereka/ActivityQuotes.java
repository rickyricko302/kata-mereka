package com.hikki.katamereka;

import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.hikki.katamereka.adapter.QuoteAdapter;
import com.hikki.katamereka.databinding.ActivityQuotesBinding;
import com.hikki.katamereka.model.QuoteModel;
import com.hikki.katamereka.viewmodel.MyViewModelFactory;
import com.hikki.katamereka.viewmodel.QuotesViewModel;

import java.io.File;

public class ActivityQuotes extends AppCompatActivity {

    ActivityQuotesBinding binding;
    QuotesViewModel viewModel;
    QuoteAdapter adapter;
    String key;
    Button nextPage,backPage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_quotes);
        key = getIntent().getStringExtra("key");
        binding.quotesJUdul.setText(key.replace("-"," ").replace("_"," "));
        nextPage = binding.quoteNext;
        backPage = binding.quoteBack;
        viewModel = new ViewModelProvider(this,new MyViewModelFactory(getApplication(),key)).get(QuotesViewModel.class);
        //lanjut esokk
        setUp();
        viewModel.getData().observe(this, new Observer<QuoteModel>() {
            @Override
            public void onChanged(QuoteModel quoteModel) {
                binding.lottieQuotes.setVisibility(View.GONE);
                if(quoteModel != null){
                    adapter.setData(quoteModel.getQuotes(),key.replace("-"," ").replace("_"," "));
                    if(binding.quoteRecycler.getVisibility() == View.GONE){
                        binding.quoteRecycler.setVisibility(View.VISIBLE);
                    }
                    if(viewModel.getHasNexts()){
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
                    dialog();
                }
                else{
                    binding.quoteCobaLagi.setVisibility(View.VISIBLE);
                   // Toast.makeText(ActivityQuotes.this, "koneksi error", Toast.LENGTH_SHORT).show();
                    //finish();
                }
            }
        });
        nextPage.setOnClickListener(v->{
            viewModel.setPage(viewModel.getPaging()+1);
            viewModel.getQuoteTokoh(viewModel.getPaging());
            binding.quotesPage.setText("Page "+viewModel.getPaging());
            binding.quoteRecycler.setVisibility(View.GONE);
            binding.lottieQuotes.setVisibility(View.VISIBLE);
            nextPage.setVisibility(View.GONE);
            backPage.setVisibility(View.GONE);
        });
        backPage.setOnClickListener(v->{
            viewModel.setPage(viewModel.getPaging()-1);
            viewModel.getQuoteTokoh(viewModel.getPaging());
            binding.quotesPage.setText("Page "+viewModel.getPaging());
            binding.quoteRecycler.setVisibility(View.GONE);
            binding.lottieQuotes.setVisibility(View.VISIBLE);
            nextPage.setVisibility(View.GONE);
            backPage.setVisibility(View.GONE);
        });
        binding.quoteCobaLagi.setOnClickListener(v -> {
            binding.quoteCobaLagi.setVisibility(View.GONE);
            binding.lottieQuotes.setVisibility(View.VISIBLE);
            viewModel.getQuoteTokoh(viewModel.getPaging());
        });
    }
    private void setUp(){
        binding.quoteRecycler.setLayoutManager(new LinearLayoutManager(this));
        adapter = new QuoteAdapter();
        binding.quoteRecycler.setAdapter(adapter);
    }

    public void dialog(){
        File f = new File(getApplicationContext().getFilesDir(),"first.log");
        if(!f.exists()){
            f.mkdir();
            MaterialAlertDialogBuilder ab = new MaterialAlertDialogBuilder(ActivityQuotes.this);
            ab.setTitle("Petunjuk Dari Pembuat!");
            ab.setCancelable(false);
            ab.setMessage("1. Tekan biasa pada kotak kata untuk mengcopy kata\n\n2. Tekan lama pada kotak kata untuk menjadikannya gambar\n\n* Petunjuk ini hanya tampil sekali saja");
            ab.setPositiveButton("Mengerti",null);
            ab.show();
        }
    }
}