package com.example.iit2014094.autotaskerapp.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.iit2014094.autotaskerapp.CustomiseWifi;
import com.example.iit2014094.autotaskerapp.DatabaseHandler;
import com.example.iit2014094.autotaskerapp.MainActivity;
import com.example.iit2014094.autotaskerapp.R;
import com.example.iit2014094.autotaskerapp.models.WifiLocations;

import java.util.ArrayList;

import static com.example.iit2014094.autotaskerapp.MainActivity.adapter;

/**
 * Created by VOJJALA TEJA on 24-03-2017.
 */

public class WifiRvAdapter extends RecyclerView.Adapter<WifiRvAdapter.WifiLocationHolder> {
    private ArrayList<WifiLocations> wifiLocations;
    private Context context;

    public WifiRvAdapter(ArrayList<WifiLocations> wifiLocations, Context context)
    {
        this.wifiLocations = wifiLocations;
        this.context = context;
    }

    @Override
    public WifiRvAdapter.WifiLocationHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.wifi_card_view, parent, false);
        WifiLocationHolder wifiLocationHolder = new WifiLocationHolder(v);
        return wifiLocationHolder;
    }

    public class WifiLocationHolder extends RecyclerView.ViewHolder implements View.OnClickListener,View.OnLongClickListener{

        CardView cardView;
        TextView wifiMacAddress;
        TextView wifiName;
        public WifiLocationHolder(View itemView) {
            super(itemView);
            cardView = (CardView)itemView.findViewById(R.id.wifiCardView);
            wifiMacAddress = (TextView)itemView.findViewById(R.id.wifiMacAddress);
            wifiName = (TextView)itemView.findViewById(R.id.wifiName);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View v) {
            Toast.makeText(context,"hello",Toast.LENGTH_LONG).show();
            Intent intent = new Intent(context, CustomiseWifi.class);
            int position = getAdapterPosition();

            intent.putExtra("id",wifiLocations.get(position).getID());
            intent.putExtra("name",wifiLocations.get(position).getName());
            intent.putExtra("mac",wifiLocations.get(position).getMacAddress());

            context.startActivity(intent);
        }


        @Override
        public boolean onLongClick(View view) {
            int position = getAdapterPosition();
            DatabaseHandler databaseHandler = new DatabaseHandler(context);
            databaseHandler.deleteWifi(wifiLocations.get(position));
            wifiLocations.remove(position);
            adapter = new WifiRvAdapter(new ArrayList<WifiLocations>(databaseHandler.getAllWifis()),context);
            adapter.notifyDataSetChanged();
            return true;
        }
    }

    @Override
    public void onBindViewHolder(WifiRvAdapter.WifiLocationHolder holder, int position) {
        WifiLocations location=wifiLocations.get(position);
        holder.wifiName.setText(location.getName());
        holder.wifiMacAddress.setText(location.getMacAddress());
    }

    @Override
    public int getItemCount() {
        return wifiLocations.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }
}
