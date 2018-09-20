package com.example.shamsuddha.tourmate;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;


public class TravelEventExpenseAdapter extends ArrayAdapter {

    private Context mContext;
    private List<TravelExpense> travelExpenseList;

    public TravelEventExpenseAdapter(@NonNull Context context, List<TravelExpense> travelExpenseList) {


        super(context, R.layout.travel_expense_model, travelExpenseList);

        mContext = context;
        this.travelExpenseList = travelExpenseList;




    }

    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(R.layout.travel_expense_model, parent, false);


        TravelExpense travelExpense = travelExpenseList.get(position);


        TextView expenseDetailsTextView = convertView.findViewById(R.id.expenseDetailsTextView);
        TextView expenseAmountTextView = convertView.findViewById(R.id.expenseAmountTextView);

        expenseDetailsTextView.setText(travelExpenseList.get(position).getExpenseDetails());
        expenseAmountTextView.setText(travelExpenseList.get(position).getExpenseAmount());
        return convertView;







} }
