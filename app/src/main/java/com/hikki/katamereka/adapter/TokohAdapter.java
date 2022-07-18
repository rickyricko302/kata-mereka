package com.hikki.katamereka.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;
import com.hikki.katamereka.R;
import com.hikki.katamereka.databinding.ItemTokohBinding;
import com.hikki.katamereka.model.TokohModel;

import java.util.ArrayList;
import java.util.List;

public class TokohAdapter extends RecyclerView.Adapter<TokohAdapter.ViewHolder> implements Filterable {
    private List<TokohModel.Tokoh> data;
    private ArrayList<TokohModel.Tokoh> arraylist;

    private onItemClick  onItemClick;
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemTokohBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_tokoh,parent,false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if(position <9){
            holder.binding.tokohNumber.setText("0"+String.valueOf(position+1));
        }
        else{
            holder.binding.tokohNumber.setText(String.valueOf(position+1));
        }
        holder.binding.namaTokoh.setText(arraylist.get(position).getNama().replace("_"," ").replace("-"," "));
        TokohModel.Tokoh tokoh = arraylist.get(position);
        holder.bind(tokoh);
    }

    @Override
    public int getItemCount() {
        if(arraylist != null){
            return arraylist.size();
        }
        return 0;
    }

    public void setData(List<TokohModel.Tokoh> data){
        this.data =data;
        arraylist = new ArrayList<>();
        //null?
        arraylist.addAll(data);
        notifyDataSetChanged();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence cari) {
                cari = cari.toString().toLowerCase().trim();
                List<TokohModel.Tokoh> filtered = new ArrayList<>();
                if(cari.length() == 0){
                    filtered = data;
                }
                else{
                    for(TokohModel.Tokoh tokoh : data){
                        if(tokoh.getNama().toLowerCase().contains(cari)){
                            filtered.add(tokoh);
                        }
                    }
                }
                FilterResults result = new FilterResults();
                result.values = filtered;
                return result;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                arraylist = (ArrayList<TokohModel.Tokoh>)filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public ArrayList<TokohModel.Tokoh> getTokoh(){
        return arraylist;
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        ItemTokohBinding binding;
        public ViewHolder(@NonNull ItemTokohBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            binding.cardViewTokoh.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onItemClick.onClick(view,getAdapterPosition());
                }
            });
        }

        public void bind(TokohModel.Tokoh tokoh){
            if(tokoh.getTahun().equals("")){
                tokoh.setTahun("-");
            }

            binding.setModel(tokoh);
            binding.executePendingBindings();
        }
    }
    public interface onItemClick {
        void onClick(View v, int position);
    }
    public void setClick(onItemClick onItemClick){
        this.onItemClick = onItemClick;
    }
}
