package com.example.andoid.dinord;


import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 */
public class BajeFragment extends Fragment {
    String Name = "";
    private Spinner spinner;
    int quantity = 0;
    int FinalPrice = 0;
    String BajeItem= "" ;
    String Message = "";

    public BajeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.activity_baje,container,false);
        Name = getActivity().getIntent().getExtras().getString("nameOfTheUser");
        displayName();
        spinner = (Spinner) getView().findViewById(R.id.baje_spinner);
        //spinner.setOnItemSelectedListener(AdapterView.OnItemSelectedListener);
        List<String> items = new ArrayList<String>();
        items.add("Goli Baje");
        items.add("Neerulli Baje");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        if (spinner.getSelectedItem().toString().equals("Goli Baje")) {
            BajeItem = "Goli Baje";
        }else if(spinner.getSelectedItem().toString().equals("Neerulli Baje")){
            BajeItem = "Neerulli Baje";
        }
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                // here we change layout visibility again
                if (spinner.getSelectedItem().toString().equals("Goli Baje")) {
                    BajeItem = "Goli Baje";
                    priceCalculation(quantity);
                }else if(spinner.getSelectedItem().toString().equals("Neerulli Baje")){
                    BajeItem = "Neerulli Baje";
                    priceCalculation(quantity);
                }
            }

            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        return rootView;
    }

    private void displayName() {
        String displayMessage = "Hello!! " + Name;
        TextView nameMessage = (TextView) getView().findViewById(R.id.name_display);
        nameMessage.setText(displayMessage);
    }

    private void priceCalculation(int quantity) {
        int price = 0 ;
        if(BajeItem.equals("Goli Baje")) {
            price = quantity * 8;
        }else if(BajeItem.equals("Neerulli Baje")){
            price = quantity * 7;
        }
        FinalPrice = price;
        displayPrice(price);
    }

    private void displayPrice(int number) {
        TextView priceTextView = (TextView) getView().findViewById(R.id.price_text_view);
        priceTextView.setText(NumberFormat.getCurrencyInstance().format(number));
    }

    public void increment(View view) {
        if (quantity == 25) {
            Toast.makeText(getActivity(), " You cannot have more than 25 cups of coffees", Toast.LENGTH_SHORT).show();
            return;
        }
        quantity = quantity + 1;
        display(quantity);
        priceCalculation(quantity);
    }

    public void decrement(View view) {
        if (quantity == 1) {
            Toast.makeText(getActivity(), " You cannot have less than 1 cup of coffee", Toast.LENGTH_SHORT).show();
            return;
        }
        quantity = quantity - 1;
        display(quantity);
        priceCalculation(quantity);
    }




    private void display(int number) {
        TextView quantityTextView = (TextView) getView().findViewById(R.id.quantity_text_view);
        quantityTextView.setText(String.valueOf(number));
    }




    public void submitOrder(View view) {
        if(quantity == 0){
            Toast.makeText(getActivity()," You cannot order Zero Items!!", Toast.LENGTH_SHORT).show();
        }else {
            SQLiteDatabase OrderDataBase;
            OrderDataBase = getActivity().openOrCreateDatabase("UsersAndOrders", MODE_PRIVATE, null);
            OrderDataBase.execSQL("CREATE TABLE IF NOT EXISTS orders(UserName VARCHAR,Item VARCHAR, Quantity VARCHAR, Price INTEGER);");
            OrderDataBase.execSQL("INSERT INTO orders VALUES('" + Name + "','" + BajeItem + "','" + quantity + "','"+ FinalPrice+"' );");
            Message += "Name   :  " + Name;
            Message += "\nDetails: Order for " + quantity + " plates of " + BajeItem;
            Message += "\nBill amount: $ " + FinalPrice;
            Message += "\nThank You ";
            displayMessage(Message);
        }
    }

    private void displayMessage(String message) {
        TextView priceTextView = (TextView) getView().findViewById(R.id.message_text_view);
        priceTextView.setText(message);
    }

    public void addMore(View view){
        Intent intent = new Intent(getActivity(), AddMore.class);
        intent.putExtra("nameOfTheUser", Name);
        startActivity(intent);
    }

}
