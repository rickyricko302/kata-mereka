package com.hikki.katamereka.adapter;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;
import com.hikki.katamereka.ActivityImg;
import com.hikki.katamereka.R;
import com.hikki.katamereka.databinding.CariKataItem;
import com.hikki.katamereka.model.CariKataModel;

import java.util.List;

import static android.content.Context.CLIPBOARD_SERVICE;

public class CariKataAdapter extends RecyclerView.Adapter<CariKataAdapter.ViewHolder> {
    private List<CariKataModel.Result> data;
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CariKataItem binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_carikata,parent,false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.binding.numberQuoteCariKata.setText(String.valueOf(position+1));
        holder.setBind(data.get(position));
    }

    @Override
    public int getItemCount() {
        if(data != null){
            return data.size();
        }
        return 0;
    }

    public void setData(List<CariKataModel.Result> data){
        this.data=data;
        notifyDataSetChanged();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        CariKataItem binding;
        public ViewHolder(@NonNull CariKataItem binding) {
            super(binding.getRoot());
            this.binding = binding;
            binding.cardViewCariKata.setOnClickListener(v->{
                ClipboardManager board = (ClipboardManager) v.getContext().getSystemService(CLIPBOARD_SERVICE);
                ClipData datas = ClipData.newPlainText("quote",data.get(getAdapterPosition()).getQuote()+"\n- "+
                        data.get(getAdapterPosition()).getAuthor());
                board.setPrimaryClip(datas);
                Toast.makeText(v.getContext(), "Sukses!", Toast.LENGTH_SHORT).show();
            });
            binding.cardViewCariKata.setOnLongClickListener(v->{
                itemView.getContext().startActivity(new Intent(itemView.getContext(), ActivityImg.class).putExtra("quote",data.get(getAdapterPosition()).getQuote())
                        .putExtra("author","- "+data.get(getAdapterPosition()).getAuthor()));
                return true;
            });
        }

        public void setBind(CariKataModel.Result res){
            binding.setVm(res);
            binding.executePendingBindings();
        }
    }
}
