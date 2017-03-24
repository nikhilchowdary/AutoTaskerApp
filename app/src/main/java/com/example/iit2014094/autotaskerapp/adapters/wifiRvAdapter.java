package com.example.iit2014094.autotaskerapp.adapters;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.iit2014094.autotaskerapp.R;
import com.example.iit2014094.autotaskerapp.models.WifiLocations;

import java.util.ArrayList;

/**
 * Created by VOJJALA TEJA on 24-03-2017.
 */

public class WifiRvAdapter extends RecyclerView.Adapter<WifiRvAdapter.WifiLocationHolder> {
    private ArrayList<WifiLocations> wifiLocations;

    public WifiRvAdapter(ArrayList<WifiLocations> wifiLocations)
    {
        this.wifiLocations = wifiLocations;;
    }

    @Override
    public WifiRvAdapter.WifiLocationHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.wifi_card_view, parent, false);
        WifiLocationHolder wifiLocationHolder = new WifiLocationHolder(v);
        return wifiLocationHolder;
    }

    public class WifiLocationHolder extends RecyclerView.ViewHolder{

        CardView cardView;
        TextView wifiIcon;
        TextView wifiName;;;
        public WifiLocationHolder(View itemView) {
            super(itemView);
            cardView = (CardView)itemView.findViewById(R.id.wifiCardView);
            wifiIcon = (TextView)itemView.findViewById(R.id.wifiIcon);
            wifiName = (TextView)itemView.findViewById(R.id.wifiName);
        }
    }

    @Override
    public void onBindViewHolder(WifiRvAdapter.WifiLocationHolder holder, int position) {
        WifiLocations location=wifiLocations.get(position);
        holder.wifiName.setText("hello");

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
