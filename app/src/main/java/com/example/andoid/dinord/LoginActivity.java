package com.example.andoid.dinord;


import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {
    int counter = 3;
    private TextView attemptsLeft;
    private EditText password;
    private EditText userName;
    private Button loginBut;
    String ToastMsg = "Wrong Credentials.";
    public SQLiteDatabase DataBase;
    String PWInRecord;
    SharedPreferences myPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
       // myPreferences = getSharedPreferences("myPrefs", Context.MODE_PRIVATE);
        myPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        boolean rememberMe = myPreferences.getBoolean("rememberMe", false);
        if (rememberMe) {
            String login = myPreferences.getString("login", null);
            Toast.makeText(getApplicationContext(),
                    "Redirecting...", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(LoginActivity.this, detailsOfYou.class);
            intent.putExtra("nameOfTheUser", login);
            startActivity(intent);
        } else {
            loginBut = (Button) findViewById(R.id.login_button);
            Button cancelBut = (Button) findViewById(R.id.cancel_button);
            userName = (EditText) findViewById(R.id.nameID);
            password = (EditText) findViewById(R.id.passwordID);
            attemptsLeft = (TextView) findViewById(R.id.attemptCount);
            DataBase = openOrCreateDatabase("UserNamesAndPW", MODE_PRIVATE, null);
            DataBase.execSQL("CREATE TABLE IF NOT EXISTS user(NewUserName VARCHAR,CorrespondingPassWord VARCHAR);");


            loginBut.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String typedUsername = userName.getText().toString();
                    Cursor c = DataBase.rawQuery("SELECT * FROM  user WHERE NewUserName ='" + typedUsername + "';", null);
                    if (c.getCount() == 0) {
                        Toast.makeText(getApplicationContext(),
                                "Account Does not Exist..!!", Toast.LENGTH_SHORT).show();
                    } else if (password.getText().toString().equals("")) {
                        Toast.makeText(getApplicationContext(),
                                "Enter Password..", Toast.LENGTH_SHORT).show();
                    } else {
                        while (c.moveToNext()) {
                            PWInRecord = c.getString(1);
                        }
                        c.close();
                        if (password.getText().toString().equals(PWInRecord)) {
                            CheckBox rememberMeCB = (CheckBox) findViewById(R.id.rememberMeID);
                            boolean isChecked = rememberMeCB.isChecked();
                            if (isChecked) {
                                saveLoginDetails();
                            }
                            Toast.makeText(getApplicationContext(),
                                    "Redirecting...", Toast.LENGTH_SHORT).show();
                            String Name = userName.getText().toString();
                            Intent intent = new Intent(LoginActivity.this, detailsOfYou.class);
                            intent.putExtra("nameOfTheUser", Name);
                            startActivity(intent);
                        } else {
                            Toast.makeText(getApplicationContext(), ToastMsg, Toast.LENGTH_SHORT).show();
                            counter--;
                            attemptsLeft.setText(String.valueOf(counter));
                            if (counter == 1) {
                                ToastMsg += "\nTry again Next Time...";
                            }
                            if (counter == 0) {
                                loginBut.setEnabled(false);

                            }
                        }
                    }
                }

            });
            cancelBut.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        }
    }

    private void saveLoginDetails(){
        String savedUserName = userName.getText().toString();
        String savedPassword = PWInRecord;
        SharedPreferences.Editor e= myPreferences.edit();
        e.putBoolean("rememberMe", true);
        e.putString("login", savedUserName);
        e.putString("password", savedPassword);
        e.apply();
    }

    public void SignUpSection(View view) {
        Intent intent = new Intent(LoginActivity.this, SignUp.class);
        startActivity(intent);
    }

    public void displayRecord(View view) {
        String data = "";
        String buffer= "";
        DataBase = openOrCreateDatabase("UserNamesAndPW", MODE_PRIVATE, null);
        DataBase.execSQL("CREATE TABLE IF NOT EXISTS user(NewUserName VARCHAR,CorrespondingPassWord VARCHAR);");
        Cursor c = DataBase.rawQuery("SELECT * FROM  user WHERE NewUserName = 'shreevara';", null);
        if (c.getCount() == 0) {
            data = " there are no items in the database corresponding to shreevara ";
        } else {
            while (c.moveToNext()) {
                buffer += ("Username :" + c.getString(0) + "\n");
                buffer += ("Password :" + c.getString(1) + "\n");
            }
        }
            c.close();
        data += buffer;
            TextView nameMessage = (TextView) findViewById(R.id.simply_display);
            nameMessage.setText(data);
        }
    public void goToOrderList(View view){
        Intent intent = new Intent(LoginActivity.this, MyOrderList.class);
        startActivity(intent);
    }
    }
