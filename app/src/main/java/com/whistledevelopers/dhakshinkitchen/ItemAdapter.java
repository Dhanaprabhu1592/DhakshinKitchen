package com.whistledevelopers.dhakshinkitchen;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
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
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder> implements Filterable{

    public Context context;
    public ArrayList<ItemsModel> itemsModelList;
    ArrayList<ConfirmItemModel> confirmItemModel;
    public ArrayList<ItemsModel> orig;
    public static final String SHARED_PREF_NAME = "Dhakshin";


    public ItemAdapter(OrderActivity context, ArrayList<ItemsModel> itemsModelList) {
        this.context=context;
        this.itemsModelList=itemsModelList;
    }

    public ItemAdapter() {

    }


    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(context);
        View view=layoutInflater.inflate(R.layout.items_row,null);
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ItemViewHolder holder, final int position) {
        final ItemsModel model=itemsModelList.get(position);
        confirmItemModel=new ArrayList<ConfirmItemModel>();

        holder.text_name.setText(model.getName());
        holder.txt_qty.setText(model.getCount());
        holder.btn_inc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int qty=Integer.valueOf((String) holder.txt_qty.getText());
                if(qty==0){
                    qty++;
                    holder.btn_dec.setEnabled(true);

                    holder.txt_qty.setText(String.valueOf(qty));
                    SharedPreferences sp = context.getSharedPreferences(SHARED_PREF_NAME, MODE_PRIVATE);
                    SharedPreferences.Editor editor = sp.edit();

                    editor.putString("reloadArray", "true");

                    editor.apply();

                    //confirmItemModel.add(new ConfirmItemModel(model.getName(),model.getCount(),String.valueOf(qty)));
                    itemsModelList.get(holder.getAdapterPosition()).setCount(String.valueOf(qty));
                    itemsModelList.get(holder.getAdapterPosition()).setSelected(true);

                }else if(qty>0){

                    qty++;
                    //confirmItemModel.add(new ConfirmItemModel(model.getName(),model.getCount(),String.valueOf(qty)));
                    itemsModelList.get(holder.getAdapterPosition()).setCount(String.valueOf(qty));
                    itemsModelList.get(holder.getAdapterPosition()).setSelected(true);


                    holder.txt_qty.setText(String.valueOf(qty));





                }

            }

        });
        holder.btn_dec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int qty=Integer.valueOf((String) holder.txt_qty.getText());
                if(qty==0){
                    holder.btn_dec.setEnabled(false);
                    itemsModelList.get(holder.getAdapterPosition()).setSelected(true);

                }else if(qty>0){

                    qty--;
                    //confirmItemModel.add(new ConfirmItemModel(model.getName(),model.getCount(),String.valueOf(qty)));
                    itemsModelList.get(holder.getAdapterPosition()).setCount(String.valueOf(qty));
                   // itemsModelList.get(holder.getAdapterPosition()).setSelected(true);


                    holder.txt_qty.setText(String.valueOf(qty));





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
        TextView text_name,text_count,txt_qty;
        Button btn_inc,btn_dec;
        public ItemViewHolder(View itemView) {
            super(itemView);
            text_name=itemView.findViewById(R.id.txt_name);
            text_count=itemView.findViewById(R.id.txt_count);
            btn_inc=itemView.findViewById(R.id.btn_inc);
            btn_dec=itemView.findViewById(R.id.btn_dec);
            txt_qty=itemView.findViewById(R.id.txt_qty);


        }
    }
}
