package com.hikki.katamereka;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.snackbar.Snackbar;
import com.hikki.katamereka.adapter.WarnaAdapter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ActivityImg extends AppCompatActivity
{
    HorizontalScrollView hsv;
    RelativeLayout rl;
    TextView text,author;
    RecyclerView recyclerView;
    WarnaAdapter adapter;
    Button simpan,kembali,zoomout,zoomin;
    List<Drawable> data = new ArrayList<>();
    boolean vh;
    int e,col,cols;
    float scale = 0.5f;
    RelativeLayout rootImg;
    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        // TODO: Implement this method
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_img);
        hsv = findViewById(R.id.hsv);
        rl = findViewById(R.id.rlquote);
        text = findViewById(R.id.textrl1);
        simpan = findViewById(R.id.qsave);
        kembali = findViewById(R.id.qkembali);
        zoomout = findViewById(R.id.zoomout);
        zoomin = findViewById(R.id.zoomin);
        rootImg = findViewById(R.id.rootImg);
        author = findViewById(R.id.textAuthora);
        recyclerView = findViewById(R.id.recyclerWarna);
        author.setText(getIntent().getStringExtra("author"));
        text.setText(getIntent().getStringExtra("quote"));
        recyclerView.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false));
        adapter = new WarnaAdapter(data);
        recyclerView.setAdapter(adapter);
        data.add(getDrawable(R.drawable.gradient1));
        data.add(getDrawable(R.drawable.gradient2));
        data.add(getDrawable(R.drawable.gradient3));
        data.add(getDrawable(R.drawable.gradient4));
        data.add(getDrawable(R.drawable.gradient5));
        data.add(getDrawable(R.drawable.gradient6));
        data.add(getDrawable(R.drawable.gradient7));
        adapter.setClick(new WarnaAdapter.OnClickListeners() {
            @Override
            public void onClick(View v, int position) {
                rl.setBackground(data.get(position));
                //linKata.setBackground(data.get(position));
                //  Toast.makeText(Activity_Image.this, "ok", Toast.LENGTH_SHORT).show();
            }
        });
        //onclick event
        kembali.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View p1)
            {
                // TODO: Implement this method
                finish();
            }
        });
        zoomout.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View p1)
            {
                // TODO: Implement this method
                scale-=0.1f;
                rl.setScaleX(scale);
                rl.setScaleY(scale);
                getCenterItem(rl);
            }
        });
        zoomin.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View p1)
            {
                // TODO: Implement this method
                scale+=0.1f;
                rl.setScaleX(scale);
                rl.setScaleY(scale);
                getCenterItem(rl);
            }
        });
        simpan.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View p1)
            {
                // TODO: Implement this method
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                    checkp();
                }
                else{
                    save();
                }
            }
        });


        hsv.setOnTouchListener(new HorizontalScrollView.OnTouchListener(){

            @Override
            public boolean onTouch(View p1, MotionEvent p2)
            {
                // TODO: Implement this method
                return true;
            }
        });
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus)
    {
        // TODO: Implement this method
        super.onWindowFocusChanged(hasFocus);
        if(!vh){
            vh=true;
            rl.setBackground(getDrawable(R.drawable.gradient2));   //   Intent in = getIntent();
           //text.setText(in.getStringExtra("quote"));
            //text.setText("Keep smiling\nPoom");
           // rl.getLayoutParams().height = rl.getWidth();
            //rl.requestLayout();
            getCenterItem(rl);
            cols = 0;
        }
    }

    void getCenterItem(View view) {
        // Get the width of horizontalScrollView
        int hsvWidth = hsv.getWidth();
        // Get the position of the left edge of the textview
        int textViewLeft = view.getLeft();
        // Get the width of the textview Item
        int textViewWidth = view.getWidth();
        // Calculate the offset
        int offset = textViewLeft + textViewWidth / 2 - hsvWidth / 2;
        // Horizontal smooth scroll offset
        hsv.smoothScrollTo(offset, 0);
    }
    public void save(){
        Bitmap p = Bitmap.createBitmap(rl.getWidth(),rl.getHeight(),Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(p);
        rl.draw(c);
        try{
            File f = new File("/sdcard/Pictures/Kata-Mereka");
            if(!f.exists()){
                f.mkdir();
            }
            Date dNow = new Date( );
            SimpleDateFormat ft = new SimpleDateFormat ("dd_hh_mm_ss");
            String path = "/sdcard/Pictures/Kata-Mereka/IMG_"+ft.format(dNow).toString()+".jpg";
            FileOutputStream fo = new FileOutputStream(new File(path));
            p.compress(Bitmap.CompressFormat.JPEG,100,fo);
            fo.close();
            fo.flush();
            Snackbar sn = Snackbar.make(rootImg,"Sukses simpan difolder \nPictures/Kata-Mereka/IMG_"+ft.format(dNow).toString()+".jpg",Snackbar.LENGTH_INDEFINITE);
            sn.setTextColor(Color.parseColor("#FFBDBDBD"));
            sn.setBackgroundTint(Color.RED);
            sn.setAnimationMode(Snackbar.ANIMATION_MODE_FADE);
            sn.setActionTextColor(Color.WHITE);
            sn.setAction("Buka", new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    sn.dismiss();
                    Intent in = new Intent(Intent.ACTION_VIEW);
                    in.setDataAndType(FileProvider.getUriForFile(getApplicationContext(),getApplicationContext().getPackageName()+".provider",new File(path)),"image/*");
                    in.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    startActivity(in);
                }
            });
            sn.show();

            //Toast.makeText(getApplicationContext(),"Sukses simpan difolder \nPictures/Kata-Mereka/IMG_"+ft.format(dNow).toString()+".jpg",Toast.LENGTH_LONG).show();

        }catch(IOException e){}
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    private void checkp(){
        String[] p = {Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE};
        if(checkSelfPermission(p[0]) != PackageManager.PERMISSION_GRANTED){
            requestPermissions(p,1912);
        }
        else{
            save();
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(checkSelfPermission(permissions[0]) == PackageManager.PERMISSION_GRANTED){
            save();
        }else{
            MaterialAlertDialogBuilder ab = new MaterialAlertDialogBuilder(ActivityImg.this);
            ab.setTitle("Upps perizinan kamu tolak");
            ab.setCancelable(false);
            ab.setMessage("Aplikasi ini membutuhkan perizinan untuk menyimpannya ke Gallery!");
            ab.setPositiveButton("Mengerti",null);
            ab.show();
        }
    }
}

