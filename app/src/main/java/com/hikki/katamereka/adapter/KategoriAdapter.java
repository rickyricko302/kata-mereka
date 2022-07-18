package com.hikki.katamereka.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.card.MaterialCardView;
import com.hikki.katamereka.R;

import java.util.ArrayList;

public class KategoriAdapter extends RecyclerView.Adapter<KategoriAdapter.ViewHolder> {
    private ArrayList<String> data;
    private onClickListener onClick;
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_kategori,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if(position <9){
            holder.number.setText("0"+String.valueOf(position+1));
        }
        else{
            holder.number.setText(String.valueOf(position+1));
        }
        String kata = data.get(position);
        holder.kategori.setText(kata.replace("-"," ").replace("_"," "));
    }

    @Override
    public int getItemCount() {
        if(data !=null){
            return data.size();
        }
        return 0;
    }

    public void setData(ArrayList<String> data){
        this.data = data;
        notifyDataSetChanged();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView kategori,number;
        MaterialCardView card;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            kategori = itemView.findViewById(R.id.kategoriText);
            card = itemView.findViewById(R.id.cardViewKategori);
            number = itemView.findViewById(R.id.kategoriNumber);
            card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onClick.onItemClick(view,getAdapterPosition());
                }
            });
        }
    }

    public interface onClickListener{
        void onItemClick(View v, int position);
    }

    public void setOnClick(onClickListener onClick){
        this.onClick = onClick;
    }
}
