package com.example.shamsuddha.tourmate;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;
public class MemoryAdapter extends ArrayAdapter {
    private Context mContext;
    private List<Memories> memoryList;
    public MemoryAdapter(@NonNull Context context, List<Memories>memoryList) {
        super(context, R.layout.memory_model, memoryList);
        mContext = context;
        this.memoryList = memoryList;
    }
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(R.layout.memory_model, parent, false);
        //  Memories memories = memoryList.get(position);

        TextView eventMemoriesCaptionTextView = convertView.findViewById(R.id.eventMemoriesCaptionTextView);

        eventMemoriesCaptionTextView.setText(memoryList.get(position).getMemoryDetails());

        ImageView eventMemories = convertView.findViewById(R.id.eventMemories);


     //   Log.d("AAA",memoryList.get(position).getUri());
     //   System.out.println(memoryList.get(position).getUri());


        Picasso.get()
                .load(memoryList.get(position).getUri())
                .into(eventMemories);






        return convertView;

    }

}
