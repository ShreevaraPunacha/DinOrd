package com.example.andoid.dinord;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

public class coffee extends AppCompatActivity {
    int quantity = 0;
    int FinalPrice = 0;
    String Name = "";
    String Message = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coffee);
        Bundle bundle = getIntent().getExtras();
        Name = bundle.getString("nameOfTheUser");
        displayName();

    }

    public void submitOrder(View view) {
        if(quantity == 0){
            Toast.makeText(getApplicationContext()," You cannot order Zero Items!!", Toast.LENGTH_SHORT).show();
        }else {
            SQLiteDatabase OrderDataBase;
            OrderDataBase = openOrCreateDatabase("UsersAndOrders", MODE_PRIVATE, null);
            OrderDataBase.execSQL("CREATE TABLE IF NOT EXISTS user(UserName VARCHAR,Item VARCHAR, Quantity VARCHAR , Price INTEGER);");
            CheckBox WhippedCreamCheckbox = (CheckBox) findViewById(R.id.WhippedCream_checkbox);
            boolean hasWhippedCream = WhippedCreamCheckbox.isChecked();
            // Log.v("MainActivity","Has Whipped Cream" + hasWhippedCream);
            //extracting checkbox status to assign to a boolean variable
            CheckBox ChocolateCheckbox = (CheckBox) findViewById(R.id.Chocolate_checkbox);
            boolean hasChocolate = ChocolateCheckbox.isChecked();
            String DataSave = "Coffee ";
            if (hasWhippedCream) {
                DataSave += "+ WC ";
            }
            if (hasChocolate) {
                DataSave += "+ C ";
            }
            OrderDataBase.execSQL("INSERT INTO orders VALUES('" + Name + "','" + DataSave + "','" + quantity + "','"+FinalPrice+"' );");
            Message += "Name   :  " + Name;
            Message += "\nDetails: Order for " + quantity + " cups of coffee";
            Message += "\n           Whipped Cream Required ??" + " " + hasWhippedCream;
            Message += "\n           Chocolate Required ??" + " " + hasChocolate;
            Message += "\nBill amount: $ " + FinalPrice;
            Message += "\nThank You ";
            displayMessage(Message);
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

    public void currentPrice(View view) {
        priceCalculation(quantity);
    }

    private void priceCalculation(int quantity) {
        int pricePerCup = 5;
        CheckBox ChocolateCheckbox = (CheckBox) findViewById(R.id.Chocolate_checkbox);
        boolean hasChocolate = ChocolateCheckbox.isChecked();
        if (hasChocolate) {
            pricePerCup += 1;
        }
        CheckBox WhippedCreamCheckbox = (CheckBox) findViewById(R.id.WhippedCream_checkbox);
        boolean hasWhippedCream = WhippedCreamCheckbox.isChecked();
        if (hasWhippedCream) {
            pricePerCup += 2;
        }
        int price = quantity * pricePerCup;
        FinalPrice = price;
        displayPrice(price);
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText(String.valueOf(number));
    }

    /**
     * This method displays the given price on the screen.
     */
    private void displayPrice(int number) {
        TextView priceTextView = (TextView) findViewById(R.id.price_text_view);
        priceTextView.setText(NumberFormat.getCurrencyInstance().format(number));
    }

    /**
     * This method displays the given text on the screen.
     */
    private void displayMessage(String message) {
        TextView priceTextView = (TextView) findViewById(R.id.message_text_view);
        priceTextView.setText(message);
    }

    public void sendEmail(View view) {
        String subjectField = " Just Java coffee Order for " + Name;
        String msgContent = Message;
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_TEXT, msgContent);
        intent.putExtra(Intent.EXTRA_SUBJECT, subjectField);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }

    }
    private void displayName() {
        String displayMessage= "Hello!! " + Name;
        TextView nameMessage = (TextView) findViewById(R.id.name_display);
        nameMessage.setText(displayMessage);
    }
    public void addMore(View view){
        Intent intent = new Intent(coffee.this, AddMore.class);
        intent.putExtra("nameOfTheUser", Name);
        startActivity(intent);
    }
}





