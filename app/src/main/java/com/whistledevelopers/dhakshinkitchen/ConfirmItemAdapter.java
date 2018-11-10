package com.whistledevelopers.dhakshinkitchen;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.List;

public class ConfirmItemAdapter extends RecyclerView.Adapter<ConfirmItemAdapter.ConfirmViewHolder> {
    List<ItemsModel> itemModels;
    Context context;
    public ConfirmItemAdapter(ConfirmOrderActivity context, List<ItemsModel> itemModels) {
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

        ItemsModel confirmItemModel=itemModels.get(position);
        if(confirmItemModel.isSelected()){
            holder.name.setText(confirmItemModel.getName());
            holder.txt_qty.setText(confirmItemModel.getCount());
        }else{
            holder.relativeLayoutMaster.setVisibility(View.GONE);

        }

    }

    @Override
    public int getItemCount() {
        return itemModels.size();
    }

    public class ConfirmViewHolder extends RecyclerView.ViewHolder {
        TextView name,count,txt_qty;
        CardView relativeLayoutMaster;
        public ConfirmViewHolder(View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.txt_name);
            count=itemView.findViewById(R.id.txt_count);
            txt_qty=itemView.findViewById(R.id.txt_qty);
            relativeLayoutMaster=itemView.findViewById(R.id.master_layout);
        }
    }
}
