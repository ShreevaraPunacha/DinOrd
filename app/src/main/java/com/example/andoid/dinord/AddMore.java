package com.example.andoid.dinord;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;

public class AddMore extends AppCompatActivity {

    String Name = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_more);
        Bundle bundle = getIntent().getExtras();
        Name = bundle.getString("nameOfTheUser");
        displayName();
        ArrayList<word> words = new ArrayList<word>();
        words.add(new word("Baje","$"));
        words.add(new word("Chapathi" , "$"));
        words.add(new word("Coffee","$10"));
        words.add(new word("Cold Items" , "$"));
        words.add(new word("Idli Vade / Ksheera", "$"));
        words.add(new word("Masala Dosa" , "$"));
        words.add(new word("Parota" , "$"));
        words.add(new word("Plain Dosa" , "$"));
        words.add(new word("Tea" , "$"));
        words.add(new word("Thuppa Dosa" , "$"));

        WordAdapter adapter = new WordAdapter(this, words);
        ListView listView = (ListView) findViewById(R.id.list);

        listView.setAdapter(adapter);



        /*AdapterView.OnItemSelectedListener spinnerListener = new AdapterView.OnItemSelectedListener(this,adapter);
        spinner.setOnItemSelectedListener(spinnerListener);*/
    }

    private void displayName() {
        String displayMessage = "Hello!! " + Name;
        TextView nameMessage = (TextView) findViewById(R.id.name_display);
        nameMessage.setText(displayMessage);
    }
}