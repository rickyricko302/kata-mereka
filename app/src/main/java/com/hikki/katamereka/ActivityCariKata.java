package com.hikki.katamereka;

import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.hikki.katamereka.adapter.CariKataAdapter;
import com.hikki.katamereka.adapter.QuoteAdapter;
import com.hikki.katamereka.databinding.ActivityCariKataBinding;
import com.hikki.katamereka.model.CariKataModel;
import com.hikki.katamereka.viewmodel.CariKataViewModel;
import com.hikki.katamereka.viewmodel.MyViewModelFactory;

import java.io.File;

public class ActivityCariKata extends AppCompatActivity {

    ActivityCariKataBinding binding;
    CariKataViewModel viewModel;
    CariKataAdapter adapter;
    String kata;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_cari_kata);
        kata = getIntent().getStringExtra("kata");
        binding.quotesJUdul2.setText("Kata "+kata);
        setUP();
        viewModel = new ViewModelProvider(this, new MyViewModelFactory(getApplication(),kata)).get(CariKataViewModel.class);
       binding.setLifecycleOwner(this);
        viewModel.getData().observe(this, new Observer<CariKataModel>() {
            @Override
            public void onChanged(CariKataModel cariKataModel) {
                binding.lottieQuotes2.setVisibility(View.GONE);

                if(cariKataModel != null){
                    if(cariKataModel.getStatus().equals("kata tidak ditemukan")){

                        Toast.makeText(ActivityCariKata.this, "Kata tidak ditemukan", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        adapter.setData(cariKataModel.getResults());
                        //  Toast.makeText(ActivityCariKata.this,String.valueOf(cariKataModel.getResults().size()), Toast.LENGTH_SHORT).show();
                        binding.quoteRecycler2.setVisibility(View.VISIBLE);
                        if (viewModel.getHasNexts()) {
                            binding.quoteNext2.setVisibility(View.VISIBLE);
                            binding.quoteNext2.setText("Page " + String.valueOf(viewModel.getPaging() + 1));
                        } else {
                            binding.quoteNext2.setVisibility(View.GONE);
                        }
                        if (viewModel.getPaging() > 1) {
                            binding.quoteBack2.setVisibility(View.VISIBLE);
                            binding.quoteBack2.setText("Page " + String.valueOf(viewModel.getPaging() - 1));
                        } else {
                            binding.quoteBack2.setVisibility(View.GONE);
                        }
                        dialog();
                    }
                }
                else{
                        binding.quoteCobaLagi2.setVisibility(View.VISIBLE);
                        Toast.makeText(ActivityCariKata.this, "Koneksi Timeout / Error", Toast.LENGTH_SHORT).show();
                    //finish();
                }
            }
        });
        binding.quoteNext2.setOnClickListener(v->{
            viewModel.setPage(viewModel.getPaging()+1);
            viewModel.getCariKata(viewModel.getPaging());
            binding.quotesPage2.setText("Page "+viewModel.getPaging());
            binding.quoteRecycler2.setVisibility(View.GONE);
            binding.lottieQuotes2.setVisibility(View.VISIBLE);
            binding.quoteNext2.setVisibility(View.GONE);
            binding.quoteBack2.setVisibility(View.GONE);
        });
        binding.quoteBack2.setOnClickListener(v->{
            viewModel.setPage(viewModel.getPaging()-1);
            viewModel.getCariKata(viewModel.getPaging());
            binding.quotesPage2.setText("Page "+viewModel.getPaging());
            binding.quoteRecycler2.setVisibility(View.GONE);
            binding.lottieQuotes2.setVisibility(View.VISIBLE);
            binding.quoteNext2.setVisibility(View.GONE);
            binding.quoteBack2.setVisibility(View.GONE);
        });
        binding.quoteCobaLagi2.setOnClickListener(v -> {
            binding.quoteCobaLagi2.setVisibility(View.GONE);
            binding.lottieQuotes2.setVisibility(View.VISIBLE);
            viewModel.getCariKata(viewModel.getPaging());
        });
    }
    public void dialog(){
        File f = new File(getApplicationContext().getFilesDir(),"first.log");
        if(!f.exists()){
            f.mkdir();
            MaterialAlertDialogBuilder ab = new MaterialAlertDialogBuilder(ActivityCariKata.this);
            ab.setTitle("Petunjuk Dari Pembuat!");
            ab.setCancelable(false);
            ab.setMessage("1. Tekan biasa pada kotak kata untuk mengcopy kata\n\n2. Tekan lama pada kotak kata untuk menjadikannya gambar\n\n* Petunjuk ini hanya tampil sekali saja");
            ab.setPositiveButton("Mengerti",null);
            ab.show();
        }
    }

    private void setUP() {
        binding.quoteRecycler2.setLayoutManager(new LinearLayoutManager(this));
        adapter = new CariKataAdapter();
        binding.quoteRecycler2.setAdapter(adapter);
    }


}