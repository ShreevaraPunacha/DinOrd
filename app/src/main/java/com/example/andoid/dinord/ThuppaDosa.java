package com.example.andoid.dinord;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

public class ThuppaDosa extends AppCompatActivity {
    String Name = "";
    int quantity = 0;
    int FinalPrice = 0;
    String Message = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thuppa_dosa);
        Bundle bundle = getIntent().getExtras();
        Name = bundle.getString("nameOfTheUser");
        displayName();
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
        int price = quantity * 10;
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
            OrderDataBase.execSQL("INSERT INTO orders VALUES('" + Name + "','Thuppa Dosa','" + quantity + "' , '" + FinalPrice+"' );");
            Message += "Name   :  " + Name;
            Message += "\nDetails: Order for " + quantity + " plates of Thuppa Dosa";
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
        Intent intent = new Intent(ThuppaDosa.this, AddMore.class);
        intent.putExtra("nameOfTheUser", Name);
        startActivity(intent);
    }
}