package com.example.andoid.dinord;


import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class AccountCreated extends AppCompatActivity {
    private String NewUserName;
    private String CorrespondingPassWord;
    private SQLiteDatabase DataBase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_created);
        Bundle bundle = getIntent().getExtras();
        NewUserName = bundle.getString("newUserName");
        CorrespondingPassWord = bundle.getString("correspondingPW");
        displayNameAndPW();
    }
    private void displayNameAndPW() {
        DataBase = openOrCreateDatabase("UserNamesAndPW", MODE_PRIVATE, null);
        DataBase.execSQL("CREATE TABLE IF NOT EXISTS user(NewUserName VARCHAR,CorrespondingPassWord VARCHAR);");
        insertIntoRecord();
    }
    public void  insertIntoRecord(){
        DataBase.execSQL("INSERT INTO user VALUES('"+ NewUserName +"','" + CorrespondingPassWord + "');");
        String displayMessage = " Account for Username : " + NewUserName;
        displayMessage += "\n Successfully Created";
        TextView nameMessage = (TextView) findViewById(R.id.name_display);
        nameMessage.setText(displayMessage);
    }
//    public void seeDataBase(View view){
//        OrderDataBase = openOrCreateDatabase("UsersAndOrders", MODE_PRIVATE, null);
//        OrderDataBase.execSQL("CREATE TABLE IF NOT EXISTS orders(UserName VARCHAR,Item VARCHAR, Quantity VARCHAR);");
//        Cursor c= OrderDataBase.rawQuery("SELECT * FROM orders ; " ,null);
//           String buffer =  "";
//            String data=  ""+ c.getCount() + " \n ";
//           try{while(c.moveToNext()) {
//               buffer += ("User :" + c.getString(0) + "\n");
//               buffer += ("Item :" + c.getString(1) + "\n");
//               buffer += ("Quantity :" + c.getString(2) + "\n");
//           }
//           }finally{
//               c.close();
//           }
//           data += buffer;
//           TextView nameMessage = (TextView) findViewById(R.id.simply_display);
//           nameMessage.setText(data);
//       }
//    public void seeAnotherDataBase(View view){
//        OrderDataBase = openOrCreateDatabase("UserNamesAndPW", MODE_PRIVATE, null);
//        OrderDataBase.execSQL("CREATE TABLE IF NOT EXISTS user(NewUserName VARCHAR,CorrespondingPassWord VARCHAR);");
//        Cursor c= OrderDataBase.rawQuery("SELECT * FROM user ; " ,null);
//        String buffer =  "";
//        String data=  ""+ c.getCount() + " \n ";
//       try{while(c.moveToNext()) {
//            buffer += ("User :" + c.getString(0) + "\n");
//            buffer += ("PW :" + c.getString(1) + "\n");
//
//        }
//        }finally{
//            c.close();
//        }
//        c.close();
//        data += buffer;
//        TextView nameMessage = (TextView) findViewById(R.id.simply_another_display);
//        nameMessage.setText(data);
//    }
    }

