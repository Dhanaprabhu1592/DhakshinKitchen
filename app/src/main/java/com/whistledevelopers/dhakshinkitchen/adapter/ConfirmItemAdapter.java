package com.whistledevelopers.dhakshinkitchen.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;
import com.whistledevelopers.dhakshinkitchen.activity.OrderActivity;
import com.whistledevelopers.dhakshinkitchen.model.ItemsModel;
import com.whistledevelopers.dhakshinkitchen.R;
import com.whistledevelopers.dhakshinkitchen.activity.ConfirmOrderActivity;

import java.util.List;

public class ConfirmItemAdapter extends RecyclerView.Adapter<ConfirmItemAdapter.ConfirmViewHolder> {
    List<ItemsModel> itemModels;
    Gson gson=new Gson();

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
        if(confirmItemModel.isSelected()&& !confirmItemModel.getCount().equals("0")){
            ConfirmOrderActivity.btn_placeorder.setEnabled(true);
            confirmItemModel.setName(OrderActivity.itemsModelList.get(position).getName());
            confirmItemModel.setCount(OrderActivity.itemsModelList.get(position).getCount());
            //model.setSelected(OrderActivity.itemsModelList.get(i).isSelected());
            ConfirmOrderActivity.confirmOrderList.add(confirmItemModel);
            ConfirmOrderActivity.txt_warning.setVisibility(View.GONE);
            ConfirmOrderActivity.btn_placeorder.setEnabled(true);
            holder.name.setText(confirmItemModel.getName());
            holder.txt_qty.setText(confirmItemModel.getCount());
            holder.btn_inc.setVisibility(View.INVISIBLE);
            holder.btn_dec.setVisibility(View.INVISIBLE);
        }else{
            holder.relativeLayoutMaster.setVisibility(View.GONE);

        }
        ConfirmOrderActivity.dataList=gson.toJson(ConfirmOrderActivity.confirmOrderList);


    }

    @Override
    public int getItemCount() {
        return itemModels.size();
    }

    public class ConfirmViewHolder extends RecyclerView.ViewHolder {
        TextView name,count,txt_qty;
        Button btn_inc,btn_dec;
        CardView relativeLayoutMaster;
        public ConfirmViewHolder(View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.txt_name);
            count=itemView.findViewById(R.id.txt_count);
            txt_qty=itemView.findViewById(R.id.txt_qty);
            relativeLayoutMaster=itemView.findViewById(R.id.master_layout);
            btn_dec=itemView.findViewById(R.id.btn_dec);
            btn_inc=itemView.findViewById(R.id.btn_inc);
        }
    }
}
