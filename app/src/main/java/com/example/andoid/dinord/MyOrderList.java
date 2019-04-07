package com.example.andoid.dinord;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class MyOrderList extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_order_list);
    }
    public void seeUsersDataBase(View view){
        SQLiteDatabase DataBase;
        DataBase = openOrCreateDatabase("UserNamesAndPW", MODE_PRIVATE, null);
       DataBase.execSQL("CREATE TABLE IF NOT EXISTS user(NewUserName VARCHAR,CorrespondingPassWord VARCHAR);");
        Cursor c= DataBase.rawQuery("SELECT * FROM user ; " ,null);
        String buffer =  "";
        String data=  ""+ c.getCount() + " \n ";
        try{while(c.moveToNext()) {
            buffer += ("User :" + c.getString(0) + "\n");
            buffer += ("PW :" + c.getString(1) + "\n");

        }
        }finally{
            c.close();
        }
        c.close();
        data += buffer;
        TextView nameMessage = (TextView) findViewById(R.id.users_display);
        nameMessage.setText(data);
    }
    public void seeOrdersDataBase(View view){
        SQLiteDatabase OrderDataBase;
        OrderDataBase = openOrCreateDatabase("UsersAndOrders", MODE_PRIVATE, null);
        OrderDataBase.execSQL("CREATE TABLE IF NOT EXISTS orders(UserName VARCHAR,Item VARCHAR, Quantity VARCHAR , Price INTEGER);");
        Cursor c= OrderDataBase.rawQuery("SELECT * FROM orders ; " ,null);
        String buffer =  "";
        String data=  ""+ c.getCount() + " \n ";
        try{while(c.moveToNext()) {
            buffer += ("User :" + c.getString(0) + "\n");
            buffer += ("Item :" + c.getString(1) + "\n");
            buffer += ("Quantity :" + c.getString(2) + "\n");
            buffer += ("Price : " + c.getString(3) + "\n");
        }
        }finally{
            c.close();
        }
        data += buffer;
        TextView nameMessage = (TextView) findViewById(R.id.orders_Display);
        nameMessage.setText(data);
    }
}
