package com.qubuss.todotransfer;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.qubuss.todotransfer.domain.Transfer;

import java.util.ArrayList;

/**
 * Created by qubuss on 01.03.2017.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder> {

    private ArrayList<Transfer> arrayList = new ArrayList<>();

    public RecyclerAdapter(ArrayList<Transfer> arrayList) {
        this.arrayList = arrayList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        holder.transferIdTV.setText(arrayList.get(position).getId());
        holder.nameTV.setText(arrayList.get(position).getName());
        holder.bankNameTV.setText(arrayList.get(position).getBankName());
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }



    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView transferIdTV;
        TextView bankNameTV;
        TextView nameTV;

        public MyViewHolder(View itemView) {
            super(itemView);

            transferIdTV = (TextView) itemView.findViewById(R.id.transferIdTV);
            bankNameTV = (TextView) itemView.findViewById(R.id.bankNameTV);
            nameTV = (TextView) itemView.findViewById(R.id.nameTV);
        }

        


    }

}

