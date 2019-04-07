package com.example.andoid.dinord;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class detailsOfYou extends AppCompatActivity {
    String Name = "";
    SharedPreferences myPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_of_you);
        Bundle bundle = getIntent().getExtras();
        Name = bundle.getString("nameOfTheUser");
        displayName();

        /*AdapterView.OnItemSelectedListener spinnerListener = new AdapterView.OnItemSelectedListener(this,adapter);
        spinner.setOnItemSelectedListener(spinnerListener);*/
    }
/*public void onItemSelected(AdapterView<?> parent, View view, int position, long id){
  String item = parent.getItemAtPosition(position).toString();
    Toast.makeText(parent.getContext(),"Selected Item is :"+ item,Toast.LENGTH_LONG).show();
}
    public void onNothingSelected(AdapterView<?> arg0) {
        // TODO Auto-generated method stub
    }*/


    private void displayName() {
        String displayMessage = " Welcome!! " + Name;
        TextView nameMessage = (TextView) findViewById(R.id.name_display);
        nameMessage.setText(displayMessage);
    }

    public void ReadyToOrder(View view){
        Intent intent = new Intent(detailsOfYou.this,StartOrderHere.class);
        intent.putExtra("nameOfTheUser", Name);
        startActivity(intent);

    }
    public void MyOrders(View view){
        Intent intent = new Intent(detailsOfYou.this,MyOrdersActivity.class);
        intent.putExtra("nameOfTheUser", Name);
        startActivity(intent);
    }
    public void SignOut(View view){
        myPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor e = myPreferences.edit();
        e.putBoolean("rememberMe", false);
        e.remove("login");
        e.remove("password");
        e.apply();
        Toast.makeText(getApplicationContext(),
                "You are Signed Out successfully...", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(detailsOfYou.this,MainActivity.class);
        startActivity(intent);
    }
}