package com.example.carwashsimulatingapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.carwashsimulatingapp.MainActivity;
import com.example.carwashsimulatingapp.R;

public class AdapterQueueList extends RecyclerView.Adapter<AdapterQueueList.MyViewHolder> {

    Context context;
    String[] queueList;

    public AdapterQueueList(Context context, String[] queueList) {
        this.context = context;
        this.queueList = queueList;
    }

    @NonNull
    @Override
    public AdapterQueueList.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //inflate layout row_queue_list
        View view = LayoutInflater.from(context).inflate(R.layout.row_queue_list, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterQueueList.MyViewHolder holder, int position) {
//        Toast.makeText(context.getApplicationContext(), "pos" + position, Toast.LENGTH_SHORT).show();
        int index = position + 1;
        if (index < queueList.length) {
            holder.indexTV.setText(Integer.toString(index));
            holder.carPlateTV.setText(queueList[index]);
        }
    }

    @Override
    public int getItemCount() {
        return (queueList.length-1);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView indexTV, carPlateTV;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            //init views
            indexTV = itemView.findViewById(R.id.index);
            carPlateTV = itemView.findViewById(R.id.carPlate);
        }
    }
}
