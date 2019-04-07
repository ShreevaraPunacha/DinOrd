package com.example.andoid.dinord;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SignUp extends AppCompatActivity {
    private EditText userName;
    private EditText createdPW;
    private EditText reTypedPW;
    public SQLiteDatabase DataBase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        DataBase = openOrCreateDatabase("UserNamesAndPW", MODE_PRIVATE, null);
        DataBase.execSQL("CREATE TABLE IF NOT EXISTS user(NewUserName VARCHAR,CorrespondingPassWord VARCHAR);");
         Button signUpBut = (Button)findViewById(R.id.signUpButton);
        userName = (EditText)findViewById(R.id.nameID);
        createdPW = (EditText)findViewById(R.id.passwordID);
        reTypedPW = (EditText)findViewById(R.id.reTypePasswordID);
        signUpBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String createdPW1 = createdPW.getText().toString();
                String createdPW2 = reTypedPW.getText().toString();
                String userNameString = userName.getText().toString();
                String passwordString = createdPW.getText().toString();
                Cursor c = DataBase.rawQuery("SELECT * FROM  user WHERE NewUserName ='"+userNameString+"';", null);
                if(userNameString.equals("")){
                    Toast.makeText(getApplicationContext()," User Name cannot be empty!!  ", Toast.LENGTH_SHORT).show();
                } else if(createdPW1.equals("")|| createdPW2.equals("")){
                    Toast.makeText(getApplicationContext()," Password cannot be empty!!  ", Toast.LENGTH_SHORT).show();
                }else if(createdPW1.length()<4){
                    Toast.makeText(getApplicationContext(),"Password must be atleast 4 letters long!! ", Toast.LENGTH_SHORT).show();
                }else if (createdPW1.equals(createdPW2) && (c.getCount() == 0)){
                    Intent intent = new Intent(SignUp.this, AccountCreated.class);
                    intent.putExtra("newUserName",userNameString);
                    intent.putExtra("correspondingPW",passwordString);
                    startActivity(intent);
                }else{
                    String display = "";
                    if(c.getCount()!= 0 ){
                        display += "account already exist";
                    }else{
                        display +="Passwords do not match";
                    }
                    Toast.makeText(getApplicationContext(), display, Toast.LENGTH_SHORT).show();
                }
                c.close();

                }

            });

    }
}
