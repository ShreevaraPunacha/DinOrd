package com.example.andoid.dinord;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class StartOrderHere extends AppCompatActivity {
    private Spinner spinner1;
    String Name = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_order_here);
        Bundle bundle = getIntent().getExtras();
        Name = bundle.getString("nameOfTheUser");
        displayName();
        Spinner spinner = (Spinner) findViewById(R.id.item_spinner);
        //spinner.setOnItemSelectedListener(AdapterView.OnItemSelectedListener);
        List<String> items = new ArrayList<String>();
        items.add("Coffee");
        items.add("Tea");
        items.add("Masala Dosa");
        items.add("Thuppa Dosa");
        items.add("Plain Dosa");
        items.add("Baje");
        items.add("Idli Vade/ Ksheera");
        items.add("Parota");
        items.add("Chapathi");
        items.add("Cold Items");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        addListenerOnButton();
    }
    public void addListenerOnButton() {
        Button btnSubmit1;

        spinner1 = (Spinner) findViewById(R.id.item_spinner);
        btnSubmit1 = (Button) findViewById(R.id.btnProceed);

        btnSubmit1.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Toast.makeText(StartOrderHere.this, "Selected Item : " + String.valueOf(spinner1.getSelectedItem()), Toast.LENGTH_SHORT).show();
                if (String.valueOf(spinner1.getSelectedItem()).equals("Coffee")) {
                    Intent intent = new Intent(StartOrderHere.this, coffee.class);
                    intent.putExtra("nameOfTheUser", Name);
                    startActivity(intent);
                } else if (String.valueOf(spinner1.getSelectedItem()).equals("Tea")) {
                    Intent intent = new Intent(StartOrderHere.this, Tea.class);
                    intent.putExtra("nameOfTheUser", Name);
                    startActivity(intent);
                } else if (String.valueOf(spinner1.getSelectedItem()).equals("Masala Dosa")) {
                    Intent intent = new Intent(StartOrderHere.this, MasalaDosa.class);
                    intent.putExtra("nameOfTheUser", Name);
                    startActivity(intent);
                } else if (String.valueOf(spinner1.getSelectedItem()).equals("Thuppa Dosa")) {
                    Intent intent = new Intent(StartOrderHere.this, ThuppaDosa.class);
                    intent.putExtra("nameOfTheUser", Name);
                    startActivity(intent);
                } else if (String.valueOf(spinner1.getSelectedItem()).equals("Plain Dosa")) {
                    Intent intent = new Intent(StartOrderHere.this, PlainDosa.class);
                    intent.putExtra("nameOfTheUser", Name);
                    startActivity(intent);
                } else if (String.valueOf(spinner1.getSelectedItem()).equals("Baje")) {
                    Intent intent = new Intent(StartOrderHere.this, Baje.class);
                    intent.putExtra("nameOfTheUser", Name);
                    startActivity(intent);
                } else if (String.valueOf(spinner1.getSelectedItem()).equals("Idli Vade/ Ksheera")) {
                    Intent intent = new Intent(StartOrderHere.this, IdliOrKsheera.class);
                    intent.putExtra("nameOfTheUser", Name);
                    startActivity(intent);
                } else if (String.valueOf(spinner1.getSelectedItem()).equals("Parota")) {
                    Intent intent = new Intent(StartOrderHere.this, Parota.class);
                    intent.putExtra("nameOfTheUser", Name);
                    startActivity(intent);
                }else if (String.valueOf(spinner1.getSelectedItem()).equals("Chapathi")) {
                    Intent intent = new Intent(StartOrderHere.this, Chapathi.class);
                    intent.putExtra("nameOfTheUser", Name);
                    startActivity(intent);
                }else if (String.valueOf(spinner1.getSelectedItem()).equals("Cold Items")) {
                    Intent intent = new Intent(StartOrderHere.this, Juice.class);
                    intent.putExtra("nameOfTheUser", Name);
                    startActivity(intent);
                }
            }
        });
    }
    private void displayName() {
        String displayMessage = "Hey! " + Name + ".. It's time to Kick Start your Party!! ";
        TextView nameMessage = (TextView) findViewById(R.id.name_display);
        nameMessage.setText(displayMessage);
    }
}
