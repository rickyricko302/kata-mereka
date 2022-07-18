package com.hikki.katamereka.adapter;

import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.hikki.katamereka.R;

import java.util.ArrayList;
import java.util.List;

public class WarnaAdapter extends RecyclerView.Adapter<WarnaAdapter.ViewHolder> {
    List<Drawable> data;
    OnClickListeners onClick;
    public WarnaAdapter(List data){

        this.data = data;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_warna,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.button.setBackground(data.get(position));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        Button button;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            button = itemView.findViewById(R.id.buttonGradient);
            button.setOnClickListener(v->{
                button.setAlpha(0);
                onClick.onClick(v,getAdapterPosition());
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        button.setAlpha(1);
                    }
                },300);
            });
        }
    }

    public interface OnClickListeners{
        void onClick(View v, int position);
    }
    public void setClick(OnClickListeners onclick){
        this.onClick=onclick;
    }
}
