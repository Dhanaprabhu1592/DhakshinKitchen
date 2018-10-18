package com.whistledevelopers.dhakshinkitchen;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder> implements Filterable{

    public Context context;
    public List<ItemsModel> itemsModelList;
    List<ConfirmItemModel> confirmItemModel;
    public ArrayList<ItemsModel> orig;

    public ItemAdapter(OrderActivity context, List<ItemsModel> itemsModelList) {
        this.context=context;
        this.itemsModelList=itemsModelList;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(context);
        View view=layoutInflater.inflate(R.layout.items_row,null);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ItemViewHolder holder, int position) {
        final ItemsModel model=itemsModelList.get(position);
        confirmItemModel=new ArrayList<ConfirmItemModel>();

        holder.text_name.setText(model.getName());
        holder.text_count.setText(model.getCount());
        holder.btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(holder.btn_add.getText().equals("+")){
                    holder.btn_add.setText("-");
                    confirmItemModel.add(new ConfirmItemModel(model.getName(),model.getCount()));
                }else if(holder.btn_add.getText().equals("- ")){
                    holder.btn_add.setText("+");
                    confirmItemModel.remove(new ConfirmItemModel(model.getName(),model.getCount()));
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return itemsModelList.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                final FilterResults oReturn = new FilterResults();
                final ArrayList<ItemsModel> results = new ArrayList<ItemsModel>();
                if (orig == null)
                    orig = (ArrayList<ItemsModel>) itemsModelList;
                if (constraint != null) {
                    if (orig != null && orig.size() > 0) {
                        for (final ItemsModel g : orig) {
                            if (g.getName().toLowerCase()
                                    .contains(constraint.toString()))
                                results.add(g);
                        }
                    }
                    oReturn.values = results;
                }
                return oReturn;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {

                itemsModelList = (ArrayList<ItemsModel>) results.values;
                notifyDataSetChanged();

            }
        };

    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView text_name,text_count;
        Button btn_add;
        public ItemViewHolder(View itemView) {
            super(itemView);
            text_name=itemView.findViewById(R.id.txt_name);
            text_count=itemView.findViewById(R.id.txt_count);
            btn_add=itemView.findViewById(R.id.btn_add);

        }
    }
}
