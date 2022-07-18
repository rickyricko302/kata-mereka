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
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.airbnb.lottie.LottieAnimationView;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.snackbar.Snackbar;
import com.hikki.katamereka.adapter.KategoriAdapter;
import com.hikki.katamereka.databinding.ActivityMainBinding;
import com.hikki.katamereka.viewmodel.HomeViewModel;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    LottieAnimationView loading;
    ActivityMainBinding binding;
    HomeViewModel viewModel;
    SearchView sv;
    Button cobalagi;
    KategoriAdapter adapter;
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main);
        sv = binding.searchView;
        loading = binding.lottieMain;
        cobalagi = binding.cobaLagi;
        recyclerView = binding.recycleKategori;
        setUpSearchView();
        viewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        binding.setLifecycleOwner(this);
        viewModel.getDataKategori().observe(this, new Observer<ArrayList<String>>() {
            @Override
            public void onChanged(ArrayList<String> strings) {
                loading.setVisibility(View.GONE);
                if(strings != null){
                    cobalagi.setVisibility(View.GONE);
                    adapter.setData(strings);
                    adapter.setOnClick(new KategoriAdapter.onClickListener() {
                        @Override
                        public void onItemClick(View v, int position) {
                          //  Toast.makeText(MainActivity.this, strings.get(position), Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(MainActivity.this,ActivityTokoh.class);
                            intent.putExtra("key",strings.get(position));
                            startActivity(intent);
                        }
                    });
                }
                else{
                  //connection erro
                    cobalagi.setVisibility(View.VISIBLE);
                    cobalagi.setAlpha(0);
                    cobalagi.animate().alpha(255);
                    Toast.makeText(MainActivity.this, "Koneksi Error", Toast.LENGTH_LONG).show();
                }
            }
        });
        cobalagi.setOnClickListener(v ->{
            loading.setVisibility(View.VISIBLE);
            cobalagi.setVisibility(View.GONE);
            viewModel.reqKategori();
        });
        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                //startactivity baru
                Intent i = new Intent(MainActivity.this,ActivityCariKata.class);
                i.putExtra("kata",s);
                startActivity(i);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
        binding.favApp.setOnClickListener(v->{
            binding.favApp.setAlpha(0f);
            MaterialAlertDialogBuilder ab = new MaterialAlertDialogBuilder(MainActivity.this);
            ab.setTitle("Tentang Aplikasi Ini");
            ab.setCancelable(false);
            ab.setMessage("1. Kata Mereka\n2. Versi 1.4\n3. Developer Ricky Verdiyanto\n\nAplikasi ini dibuat untuk mendapatkan kata bijak dari tokoh-tokoh terkenal diberbagai dunia. Aplikasi ini mempunyai fitur :\n1. Menyalin kata bijak\n2. Menyimpan kata bijak ke Gallery dengan background yang keren. \n\nSemua kata bijak ini saya dapatkan dari web jagokata.com. Terimakasih sudah menggunakan aplikasi ini :)");
            ab.setPositiveButton("Tutup",null);
            ab.show();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    binding.favApp.setAlpha(1f);
                }
            },300);
        });
      }
    public void setUpSearchView(){
        int id = sv.getContext().getResources().getIdentifier("android:id/search_src_text",null,null);
        TextView textView = (TextView) findViewById(id);
        textView.setTextColor(Color.BLACK);
        textView.setTextSize(15);
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        adapter = new KategoriAdapter();
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        sv.clearFocus();
        binding.rootMain.requestFocus();
    }
}