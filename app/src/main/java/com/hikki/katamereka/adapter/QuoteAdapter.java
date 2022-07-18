package com.hikki.katamereka.adapter;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;
import com.hikki.katamereka.ActivityImg;
import com.hikki.katamereka.R;
import com.hikki.katamereka.databinding.QuoteTokohDataBinding;

import java.util.List;

import static android.content.Context.CLIPBOARD_SERVICE;

public class QuoteAdapter extends RecyclerView.Adapter<QuoteAdapter.ViewHolder> {
    private List<String> data;
    private String author;
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        QuoteTokohDataBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),R.layout.item_quotes,parent,false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.binding.numberQuote.setText(String.valueOf(position+1));
        holder.binding.textQuote.setText(data.get(position));
    }

    @Override
    public int getItemCount() {
        if(data != null){
            return data.size();
        }
        return 0;
    }
    public void setData(List<String> data, String author){
        this.data = data;
        this.author = author;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        QuoteTokohDataBinding binding;
        public ViewHolder(@NonNull QuoteTokohDataBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            binding.cardViewQuotes.setOnClickListener(v->{
                ClipboardManager board = (ClipboardManager) v.getContext().getSystemService(CLIPBOARD_SERVICE);
                ClipData datas = ClipData.newPlainText("quote",data.get(getAdapterPosition())+"\n- "+author);
                board.setPrimaryClip(datas);
                Toast.makeText(v.getContext(), "Sukses!", Toast.LENGTH_SHORT).show();
            });
            binding.cardViewQuotes.setOnLongClickListener(v->{
                itemView.getContext().startActivity(new Intent(itemView.getContext(), ActivityImg.class).putExtra("quote",data.get(getAdapterPosition()))
                .putExtra("author","- "+author));
                return true;
            });
        }
    }

   /*

   ClipboardManager board = (ClipboardManager) binding.getRoot().getContext().getSystemService(CLIPBOARD_SERVICE);
                ClipData datas = ClipData.newPlainText("quote",data.get(getAdapterPosition())+"\n- "+author);
                board.setPrimaryClip(datas);
                Toast.makeText(v.getContext(), "Sukses!", Toast.LENGTH_SHORT).show();
    */
}
