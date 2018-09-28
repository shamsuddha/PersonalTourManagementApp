package com.example.shamsuddha.tourmate;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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



     int positionInt = getIntent().getExtras().getInt("position");

        TravelEvent travelEvent = travelEventList.get(positionInt);
        String travelEventId = travelEvent.getId();
        travelDestinationsEditText.setText(travelEvent.getTravelDestination());
        estimatedBudgetsEditText.setText(travelEvent.getEstimatedBudget());
        fromDatesEditText.setText(travelEvent.getFromDate());
        toDatesEditText.setText(travelEvent.getToDate());
        updateTravelButton.setTag(travelEventId);



    }
}
