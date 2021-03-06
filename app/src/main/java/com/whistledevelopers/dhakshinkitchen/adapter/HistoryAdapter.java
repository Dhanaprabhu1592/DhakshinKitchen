package com.whistledevelopers.dhakshinkitchen.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.whistledevelopers.dhakshinkitchen.model.OngoingModel;
import com.whistledevelopers.dhakshinkitchen.R;
import com.whistledevelopers.dhakshinkitchen.activity.HistoryActivity;

import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.OngoingViewHolder> {
    Context context;
    List<OngoingModel> ongoingModels;

    public HistoryAdapter(HistoryActivity context, List<OngoingModel> ongoingModels) {
        this.context=context;
        this.ongoingModels=ongoingModels;
    }

    @NonNull
    @Override
    public OngoingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.ongoing_row, null);
        return new OngoingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OngoingViewHolder holder, int position) {
        OngoingModel ongoingModel=ongoingModels.get(position);

        holder.orderId.setText(ongoingModel.getOrderId());
        holder.status.setText(ongoingModel.getStatus());
        holder.tableNo.setText(ongoingModel.getName());
    }

    @Override
    public int getItemCount() {
        return ongoingModels.size();
    }

    public class OngoingViewHolder extends RecyclerView.ViewHolder{
        TextView orderId,tableNo,status;
        public OngoingViewHolder(View itemView) {
            super(itemView);

            orderId=itemView.findViewById(R.id.txt_orderId);
            tableNo=itemView.findViewById(R.id.txt_tableno);
            status=itemView.findViewById(R.id.txt_status);
        }
    }
}
