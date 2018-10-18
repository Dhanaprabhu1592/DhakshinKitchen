package com.whistledevelopers.dhakshinkitchen;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class ConfirmItemAdapter extends RecyclerView.Adapter<ConfirmItemAdapter.ConfirmViewHolder> {
    List<ConfirmItemModel> itemModels;
    Context context;
    public ConfirmItemAdapter(ConfirmOrderActivity context, List<ConfirmItemModel> itemModels) {
        this.context=context;
        this.itemModels=itemModels;
    }

    @NonNull
    @Override
    public ConfirmViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(context);
        View view=layoutInflater.inflate(R.layout.items_row,null);
        return new ConfirmViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ConfirmViewHolder holder, int position) {

        ConfirmItemModel confirmItemModel=itemModels.get(position);
        holder.name.setText(confirmItemModel.getName());
        holder.count.setText(confirmItemModel.getCount());

    }

    @Override
    public int getItemCount() {
        return itemModels.size();
    }

    public class ConfirmViewHolder extends RecyclerView.ViewHolder {
        TextView name,count;
        public ConfirmViewHolder(View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.txt_name);
            count=itemView.findViewById(R.id.txt_count);
        }
    }
}
