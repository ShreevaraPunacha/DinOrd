package com.example.andoid.dinord;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

public class Juice extends AppCompatActivity {
    String Name = "";
    private Spinner coldItems;
    private LinearLayout juiceItems;
    private LinearLayout milkShakes;
    private Spinner juiceSpinner;
    private Spinner milkShakeSpinner;
    private TextView summaryTextView;
    int quantity = 0;
    int FinalPrice = 0;
    String SelectedCold = "";
    String Message = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_juice);
        Bundle bundle = getIntent().getExtras();
        Name = bundle.getString("nameOfTheUser");
        displayName();
        summaryTextView = (TextView) this.findViewById(R.id.summary);
        summaryTextView.setVisibility(TextView.GONE);
        juiceItems = (LinearLayout) this.findViewById(R.id.juice_linear_layout);
        milkShakes = (LinearLayout) this.findViewById(R.id.milkShake_linear_layout);
        coldItems = (Spinner) findViewById(R.id.cold_items_spinner);
        //spinner.setOnItemSelectedListener(AdapterView.OnItemSelectedListener);
        List<String> items = new ArrayList<String>();
        items.add("Juice Items");
        items.add("Milkshakes");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        coldItems.setAdapter(adapter);
        if (coldItems.getSelectedItem().toString().equals("Juice Items")) {
            milkShakes.setVisibility(LinearLayout.GONE);
            juiceItems.setVisibility(LinearLayout.VISIBLE);
        } else {
            milkShakes.setVisibility(LinearLayout.VISIBLE);
            juiceItems.setVisibility(LinearLayout.GONE);
        }
        coldItems.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                // here we change layout visibility again
                if (coldItems.getSelectedItem().toString().equals("Juice Items")) {
                    milkShakes.setVisibility(LinearLayout.GONE);
                    juiceItems.setVisibility(LinearLayout.VISIBLE);
                    juiceItemsSpinner();
                } else {
                    milkShakes.setVisibility(LinearLayout.VISIBLE);
                    juiceItems.setVisibility(LinearLayout.GONE);
                    milkShakesSpinner();
                }
            }

            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
    }

    private void juiceItemsSpinner() {
        SelectedCold = "Juice";
        priceCalculation(quantity);
        juiceSpinner = (Spinner) findViewById(R.id.juices_spinner);
        List<String> items = new ArrayList<String>();
        items.add("Orange");
        items.add("Mango");
        items.add("Chikku");
        items.add("Apple");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        juiceSpinner.setAdapter(adapter);
    }

    private void milkShakesSpinner() {
        SelectedCold = "MilkShake";
        priceCalculation(quantity);
        milkShakeSpinner = (Spinner) findViewById(R.id.milkShake_spinner);
        List<String> items = new ArrayList<String>();
        items.add("Chikku");
        items.add("Badam");
        items.add("Strawberry");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        milkShakeSpinner.setAdapter(adapter);
    }

    private void displayName() {
        String displayMessage = "Hello!! " + Name;
        TextView nameMessage = (TextView) findViewById(R.id.name_display);
        nameMessage.setText(displayMessage);

    }
    public void increment(View view) {
        if (quantity == 25) {
            Toast.makeText(this, " You cannot have more than 25 cups of coffees", Toast.LENGTH_SHORT).show();
            return;
        }
        quantity = quantity + 1;
        display(quantity);
        priceCalculation(quantity);
    }

    public void decrement(View view) {
        if (quantity == 1) {
            Toast.makeText(this, " You cannot have less than 1 cup of coffee", Toast.LENGTH_SHORT).show();
            return;
        }
        quantity = quantity - 1;
        display(quantity);
        priceCalculation(quantity);
    }
    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText(String.valueOf(number));
    }
    private void priceCalculation(int quantity) {
       int pricePerItem = 0;
        if(SelectedCold.equals("Juice")){
            pricePerItem = 10;
        }else if (SelectedCold.equals("MilkShake")){
            pricePerItem = 8;
        }
        int price = quantity * pricePerItem;
        FinalPrice = price;
        displayPrice(price);
    }
    private void displayPrice(int number) {
        TextView priceTextView = (TextView) findViewById(R.id.price_text_view);
        priceTextView.setText(NumberFormat.getCurrencyInstance().format(number));
    }
    public void submitOrder(View view) {
        if(quantity == 0){
            Toast.makeText(getApplicationContext()," You cannot order Zero Items!!", Toast.LENGTH_SHORT).show();
        }else {
            SQLiteDatabase OrderDataBase;
            OrderDataBase = openOrCreateDatabase("UsersAndOrders", MODE_PRIVATE, null);
            OrderDataBase.execSQL("CREATE TABLE IF NOT EXISTS user(UserName VARCHAR,Item VARCHAR, Quantity VARCHAR , Price INTEGER);");
            String DataSave = "";
            Message += "Name  :  " + Name;
            if (SelectedCold.equals("Juice")) {
                DataSave = String.valueOf(juiceSpinner.getSelectedItem()) + " Juice";
                Message += "\nDetails: Order for " + quantity + " glasses of" + String.valueOf(juiceSpinner.getSelectedItem()) + " Juice";
            } else if (SelectedCold.equals("MilkShake")) {
                DataSave = String.valueOf(milkShakeSpinner.getSelectedItem()) + " MilkShake";
                Message += "\nDetails: Order for " + quantity + " glasses of " + String.valueOf(milkShakeSpinner.getSelectedItem()) + " MilkShake";
            }
            OrderDataBase.execSQL("INSERT INTO orders VALUES('" + Name + "','" + DataSave + "','" + quantity + "' , '"+FinalPrice+"' );");
            Message += "\nBill amount: $ " + FinalPrice;
            Message += "\nThank You ";
            displayMessage(Message);
        }
    }

    private void displayMessage(String message) {
        summaryTextView.setVisibility(TextView.VISIBLE);
        TextView priceTextView = (TextView) findViewById(R.id.message_text_view);
        priceTextView.setText(message);
    }

    public void addMore(View view){
        Intent intent = new Intent(Juice.this, AddMore.class);
        intent.putExtra("nameOfTheUser", Name);
        startActivity(intent);
    }

    }
