package com.example.andoid.dinord;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

public class Tea extends AppCompatActivity {
    String Name = "";
    int quantity = 0;
    int FinalPrice = 0;
    int pricePerCupTea=0;
    String Message = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tea);
        Bundle bundle = getIntent().getExtras();
        Name = bundle.getString("nameOfTheUser");
        displayName();
    }
    private void displayName() {
        String displayMessage= "Hello!! " + Name;
        TextView nameMessage = (TextView) findViewById(R.id.name_display);
        nameMessage.setText(displayMessage);
    }
    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.normal_tea_id:
                if (checked){
                    pricePerCupTea = 7;
                    priceCalculation(quantity);

                }
                    // normal tea calculations
                    break;
            case R.id.black_tea_id:
                if (checked){
                    pricePerCupTea=5;
                    priceCalculation(quantity);
                }
                    // black tea calculation
                    break;
            case R.id.green_tea_id:
                if (checked){
                    pricePerCupTea= 9;
                    priceCalculation(quantity);
                }
                    // green tea calculation
                break;
        }
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
        int price = quantity * pricePerCupTea;
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
            String TypeOfTea;
            String DataSave = "";
            if (pricePerCupTea == 5) {
                TypeOfTea = "Black ";
            } else if (pricePerCupTea == 7) {
                TypeOfTea = "";
            } else {
                TypeOfTea = "Green ";
            }
            DataSave += TypeOfTea + "Tea";
            OrderDataBase.execSQL("INSERT INTO orders VALUES('" + Name + "','" + DataSave + "','" + quantity + "' , '" + FinalPrice+ "' );");
            Message += "Name   :  " + Name;
            Message += "\nDetails: Order for " + quantity + " cups of " + TypeOfTea + "Tea";
            Message += "\nBill amount: $ " + FinalPrice;
            Message += "\nThank You ";
            displayMessage(Message);
        }
    }
    private void displayMessage(String message) {
        TextView priceTextView = (TextView) findViewById(R.id.message_text_view);
        priceTextView.setText(message);
    }

    public void addMore(View view){
        Intent intent = new Intent(Tea.this, AddMore.class);
        intent.putExtra("nameOfTheUser", Name);
        startActivity(intent);
    }

}
