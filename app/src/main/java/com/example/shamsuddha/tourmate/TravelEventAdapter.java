package com.example.shamsuddha.tourmate;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

public class TravelEventAdapter extends ArrayAdapter {

    private Context mContext;
    private List<TravelEvent> travelEventList;

    public TravelEventAdapter(@NonNull Context context, List<TravelEvent> travelEventList) {
        super(context, R.layout.travel_event_model, travelEventList);
        mContext = context;
        this.travelEventList = travelEventList;
    }


    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(R.layout.travel_event_model, parent, false);
        TravelEvent travelEvent = travelEventList.get(position);


        TextView travelEventNameTextView = convertView.findViewById(R.id.travelEventNameTextView);
        TextView travelEventBudgetTextView = convertView.findViewById(R.id.travelEventBudgetTextView);
        TextView travelEventFromDate = convertView.findViewById(R.id.travelEventFromDate);
        TextView travelEventToTextView = convertView.findViewById(R.id.travelEventToTextView);

        travelEventNameTextView.setText(travelEventList.get(position).getTravelDestination());
        travelEventBudgetTextView.setText(travelEventList.get(position).getEstimatedBudget());
        travelEventFromDate.setText(travelEventList.get(position).getFromDate());
        travelEventToTextView.setText(travelEventList.get(position).getToDate());

        return convertView;


    }

}







