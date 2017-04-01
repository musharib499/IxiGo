package com.example.root.ixigosearchapp.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.root.ixigosearchapp.R;

/**
 * Created by root on 1/4/17.
 */

public class ViewHolderIxiGo extends RecyclerView.ViewHolder{
   public TextView tvxProvider,tvxAirPorts,tvxAirLine,tvxDistance;
    public TextView tvxArivTime,tvxPrice,tvxClass;

    public ViewHolderIxiGo(View itemView) {
        super(itemView);
        tvxProvider= (TextView) itemView.findViewById(R.id.tvProvider);
        tvxAirLine= (TextView) itemView.findViewById(R.id.tvAirline);
        tvxAirPorts= (TextView) itemView.findViewById(R.id.tvAirPort);
        tvxDistance= (TextView) itemView.findViewById(R.id.tvDistance);
        tvxPrice= (TextView) itemView.findViewById(R.id.tvxPrice);
        tvxArivTime= (TextView) itemView.findViewById(R.id.tvTime);

    }
}
