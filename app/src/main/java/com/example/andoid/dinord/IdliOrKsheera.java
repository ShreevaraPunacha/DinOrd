package com.example.andoid.dinord;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

public class IdliOrKsheera extends AppCompatActivity {
    String Name = "";
    int FinalPrice = 0;
    int quantity = 0;
    String Message = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_idli_or_ksheera);
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

    public void currentPrice(View view) {
        priceCalculation(quantity);
    }

    private void priceCalculation(int quantity) {
        int pricePerItem = 0;
        CheckBox IdliCheckbox = (CheckBox) findViewById(R.id.Idli_checkbox);
        boolean isIdli = IdliCheckbox.isChecked();
        if (isIdli) {
            pricePerItem += 6;
        }
        CheckBox KsheeraCheckbox = (CheckBox) findViewById(R.id.Ksheera_checkbox);
        boolean isKheera = KsheeraCheckbox.isChecked();
        if (isKheera) {
            pricePerItem += 4;
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
            CheckBox IdliCheckbox = (CheckBox) findViewById(R.id.Idli_checkbox);
            boolean isIdli = IdliCheckbox.isChecked();
            // Log.v("MainActivity","Has Whipped Cream" + hasWhippedCream);
            //extracting checkbox status to assign to a boolean variable
            CheckBox KsheeraCheckbox = (CheckBox) findViewById(R.id.Ksheera_checkbox);
            boolean isKheera = KsheeraCheckbox.isChecked();
            Message += "Name   :  " + Name;
            Message += "\nDetails: Order for: ";
            if (isIdli) {
                Message += "\n" + quantity + " plates of Idli Vade";
                OrderDataBase.execSQL("INSERT INTO orders VALUES('" + Name + "','Idli','" + quantity + "', '"+FinalPrice+"' );");
            }
            if (isKheera) {
                Message += "\n" + quantity + " plates of Ksheera";
                OrderDataBase.execSQL("INSERT INTO orders VALUES('" + Name + "','Ksheera','" + quantity + "','"+FinalPrice+"');");
            }
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
        Intent intent = new Intent(IdliOrKsheera.this, AddMore.class);
        intent.putExtra("nameOfTheUser", Name);
        startActivity(intent);
    }
}

