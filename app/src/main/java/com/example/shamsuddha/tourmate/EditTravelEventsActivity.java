package com.example.shamsuddha.tourmate;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

public class EditTravelEventsActivity extends AppCompatActivity {
    private List<TravelEvent> travelEventList = new ArrayList<>();
    EditText travelDestinationsEditText, estimatedBudgetsEditText,fromDatesEditText,toDatesEditText;
    Button updateTravelButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_travel_events);



        travelDestinationsEditText = findViewById(R.id.travelDestinationsEditText);
        estimatedBudgetsEditText = findViewById(R.id.estimatedBudgetsEditText);
        fromDatesEditText = findViewById(R.id.fromDatesEditText);
        toDatesEditText = findViewById(R.id.toDatesEditText);
        updateTravelButton = findViewById(R.id.updateTravelButton);



        final String id = getIntent().getStringExtra("id");
        int ids = Integer.parseInt(id);

        TravelEvent travelEvent = travelEventList.get(ids) ;
        String travelEventId = travelEvent.getId();


        travelDestinationsEditText.setText(travelEvent.getTravelDestination());
        estimatedBudgetsEditText.setText(String.valueOf(travelEvent.getEstimatedBudget()));
        fromDatesEditText.setText(String.valueOf(travelEvent.getFromDate()));
        toDatesEditText.setText(String.valueOf(travelEvent.getToDate()));
        updateTravelButton.setTag(travelEventId);



    }
}
