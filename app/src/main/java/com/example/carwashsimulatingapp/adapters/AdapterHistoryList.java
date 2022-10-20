package com.example.carwashsimulatingapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.carwashsimulatingapp.CarPlate;
import com.example.carwashsimulatingapp.R;

import java.util.ArrayList;

public class AdapterHistoryList extends RecyclerView.Adapter<AdapterHistoryList.MyViewHolder>{

    Context context;
    String[] historyList;
    ArrayList<CarPlate> detailsList;

    public AdapterHistoryList(Context context, String[] historyList, ArrayList<CarPlate> detailsList) {
        this.context = context;
        this.historyList = historyList;
        this.detailsList = detailsList;
    }

    @NonNull
    @Override
    public AdapterHistoryList.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //inflate layout row_history_list
        View view = LayoutInflater.from(context).inflate(R.layout.row_history_list, parent, false);
        return new AdapterHistoryList.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterHistoryList.MyViewHolder holder, int position) {
        String tempCarPlate = historyList[position];
        holder.carPlateTV.setText(tempCarPlate);
        int index = -1;
        for (int i = 0; i < detailsList.size(); i++) {
            if(detailsList.get(i).getCarPlate().equals(tempCarPlate)){
                index = i;
                break;
            }
        }
        if (index >= 0) {
            if (detailsList.get(index).getHasPickedUp()) {
                holder.isPickedUpTV.setText("Picked up");
            }
            else {
                holder.isPickedUpTV.setText("Not picked up");
            }
        }

    }

    @Override
    public int getItemCount() {
        return historyList.length;
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView carPlateTV, isPickedUpTV;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
//            //init views
            carPlateTV = itemView.findViewById(R.id.carPlateHistory);
            isPickedUpTV = itemView.findViewById(R.id.isPickedUp);
        }
    }
}
