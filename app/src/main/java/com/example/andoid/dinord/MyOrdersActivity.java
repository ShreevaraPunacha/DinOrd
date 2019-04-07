package com.example.andoid.dinord;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MyOrdersActivity extends AppCompatActivity {
    String Name = "";
    public SQLiteDatabase OrderDataBase;
    public SQLiteDatabase deleteDatabase;
    private LinearLayout OrderListTitle;
    private LinearLayout titleLayout;
    private LinearLayout contentLayout;
    private RelativeLayout OrderMoreButton;
    private RelativeLayout DeleteFirstSpinner;
    private RelativeLayout DeleteSecondSpinnerItem;
    private RelativeLayout DeleteSecondSpinnerQuantity;
    private RelativeLayout ProceedButtonLayout;
    private LinearLayout FilteredDisplay1;
    private LinearLayout FilteredDisplay2;
    private LinearLayout OrderCountDisplay;
    private LinearLayout OrderFunctionButton;
    private Cursor c;
    private Cursor a;
    private Spinner MainFilter;
    private Spinner ItemFilter;
    private Spinner QuantityFilter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_orders);
        OrderCountDisplay = (LinearLayout) findViewById(R.id.order_count_layout);
        FilteredDisplay1 = (LinearLayout) findViewById(R.id.filter_title_layout);
        FilteredDisplay2 = (LinearLayout) findViewById(R.id.filter_content_layout);
        DeleteFirstSpinner = (RelativeLayout) findViewById(R.id.delete_first_spinner);
        DeleteSecondSpinnerItem = (RelativeLayout) findViewById(R.id.delete_second_spinner_item);
        DeleteSecondSpinnerQuantity = (RelativeLayout) findViewById(R.id.delete_second_spinner_quantity);
        OrderMoreButton = (RelativeLayout) findViewById(R.id.order_more);
        ProceedButtonLayout = (RelativeLayout) findViewById(R.id.delete_layout);
        titleLayout = (LinearLayout) findViewById(R.id.title_layout);
        contentLayout = (LinearLayout) findViewById(R.id.content_layout);
        OrderListTitle = (LinearLayout) findViewById(R.id.order_list_title);
        OrderFunctionButton = (LinearLayout) findViewById(R.id.order_function_but);
        Bundle bundle = getIntent().getExtras();
        Name = bundle.getString("nameOfTheUser");
        FirstLayoutOfThePage();
    }

    private void FirstLayoutOfThePage() {
        OrderDataBase = openOrCreateDatabase("UsersAndOrders", MODE_PRIVATE, null);
        OrderDataBase.execSQL("CREATE TABLE IF NOT EXISTS orders(UserName VARCHAR,Item VARCHAR, Quantity VARCHAR , Price INTEGER);");
        c = OrderDataBase.rawQuery("SELECT * FROM orders WHERE UserName = '" + Name + "' ; ", null);
        String buffer = "User Name : " + Name + "\n";
        if (c.getCount() == 0) {
            buffer += "You have not ordered anything yet!!";
            TextView nameMessage = (TextView) findViewById(R.id.orders_summary_mine);
            nameMessage.setText(buffer);
            OrderFunctionButton.setVisibility(LinearLayout.GONE);
            OrderMoreButton.setVisibility(RelativeLayout.VISIBLE);
            BasicOrderFunction();
            c.close();
        } else {
            buffer += "Total number of orders: " + c.getCount() + "\n ";
            buffer += "Total Price: $" + getTotal();
            TextView nameMessage = (TextView) findViewById(R.id.orders_summary_mine);
            nameMessage.setText(buffer);
            OrderFunctionButton.setVisibility(LinearLayout.VISIBLE);
            OrderMoreButton.setVisibility(RelativeLayout.GONE);
            BasicOrderFunction();
        }

    }

    private void BasicOrderFunction() {
        titleLayout.setVisibility(LinearLayout.GONE);
        contentLayout.setVisibility(LinearLayout.GONE);
        OrderListTitle.setVisibility(LinearLayout.GONE);
        DeleteFirstSpinner.setVisibility(RelativeLayout.GONE);
        DeleteSecondSpinnerQuantity.setVisibility(RelativeLayout.GONE);
        DeleteSecondSpinnerItem.setVisibility(RelativeLayout.GONE);
        FilteredDisplay1.setVisibility(LinearLayout.GONE);
        FilteredDisplay2.setVisibility(LinearLayout.GONE);
        OrderCountDisplay.setVisibility(LinearLayout.GONE);
        ProceedButtonLayout.setVisibility(RelativeLayout.GONE);
    }

    private int getTotal() {
        int total = 0;
        c.close();
        Cursor cursor = OrderDataBase.rawQuery("SELECT SUM(price) AS Total FROM orders where UserName = '" + Name + "';", null);
        if (cursor.moveToFirst()) {
            total = cursor.getInt(cursor.getColumnIndex("Total"));
        }
        cursor.close();
        return total;
    }

    public void ViewOrderList(View view) {
        titleLayout.setVisibility(LinearLayout.VISIBLE);
        contentLayout.setVisibility(LinearLayout.VISIBLE);
        OrderListTitle.setVisibility(LinearLayout.VISIBLE);
        DeleteFirstSpinner.setVisibility(LinearLayout.GONE);
        DeleteSecondSpinnerItem.setVisibility(RelativeLayout.GONE);
        DeleteSecondSpinnerQuantity.setVisibility(RelativeLayout.GONE);
        FilteredDisplay1.setVisibility(LinearLayout.GONE);
        FilteredDisplay2.setVisibility(LinearLayout.GONE);
        OrderCountDisplay.setVisibility(LinearLayout.GONE);
        ProceedButtonLayout.setVisibility(RelativeLayout.GONE);
        String item = "";
        String quantity = "";
        String price = "";
        c = OrderDataBase.rawQuery("SELECT * FROM orders WHERE UserName = '" + Name + "' ; ", null);
        while (c.moveToNext()) {
            item += c.getString(1) + "\n";
            quantity += c.getString(2) + "\n";
            price += c.getString(3) + "\n";
        }
        c.close();
        TextView nameMessage = (TextView) findViewById(R.id.ord_disp_content_mine1);
        nameMessage.setText(item);
        nameMessage = (TextView) findViewById(R.id.ord_disp_content_mine2);
        nameMessage.setText(quantity);
        TextView priceDisp = (TextView) findViewById(R.id.ord_disp_content_mine3);
        priceDisp.setText(price);
    }

    public void OrderMore(View view) {
        Intent intent = new Intent(MyOrdersActivity.this, AddMore.class);
        intent.putExtra("nameOfTheUser", Name);
        startActivity(intent);
    }

    //delete set starts from here
    public void DeleteFunction(View view) {
        DeleteSecondSpinnerItem.setVisibility(RelativeLayout.GONE);
        DeleteSecondSpinnerQuantity.setVisibility(RelativeLayout.GONE);
        FilteredDisplay1.setVisibility(LinearLayout.GONE);
        FilteredDisplay2.setVisibility(LinearLayout.GONE);
        OrderCountDisplay.setVisibility(LinearLayout.GONE);
        ProceedButtonLayout.setVisibility(RelativeLayout.GONE);
        titleLayout.setVisibility(LinearLayout.GONE);
        contentLayout.setVisibility(LinearLayout.GONE);
        OrderListTitle.setVisibility(LinearLayout.GONE);
        OrderMoreButton.setVisibility(RelativeLayout.GONE);
        DeleteFirstSpinner.setVisibility(RelativeLayout.VISIBLE);
        MainFilter = (Spinner) findViewById(R.id.filter_by);
        List<String> items = new ArrayList<String>();
        items.add("Items");
        items.add("Quantity");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        MainFilter.setAdapter(adapter);
        if (MainFilter.getSelectedItem().toString().equals("Items")) {
            DeleteSecondSpinnerItem.setVisibility(RelativeLayout.VISIBLE);
            DeleteSecondSpinnerQuantity.setVisibility(RelativeLayout.GONE);
            FilteredDisplay1.setVisibility(LinearLayout.VISIBLE);
            FilteredDisplay2.setVisibility(LinearLayout.VISIBLE);
            OrderCountDisplay.setVisibility(LinearLayout.VISIBLE);
            ProceedButtonLayout.setVisibility(RelativeLayout.VISIBLE);
        } else if (MainFilter.getSelectedItem().toString().equals("Quantity")) {
            DeleteSecondSpinnerItem.setVisibility(RelativeLayout.GONE);
            DeleteSecondSpinnerQuantity.setVisibility(RelativeLayout.VISIBLE);
            FilteredDisplay1.setVisibility(LinearLayout.VISIBLE);
            FilteredDisplay2.setVisibility(LinearLayout.VISIBLE);
            OrderCountDisplay.setVisibility(LinearLayout.VISIBLE);
            ProceedButtonLayout.setVisibility(RelativeLayout.VISIBLE);
        }
        MainFilter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                if (MainFilter.getSelectedItem().toString().equals("Items")) {
                    DeleteSecondSpinnerItem.setVisibility(RelativeLayout.VISIBLE);
                    DeleteSecondSpinnerQuantity.setVisibility(RelativeLayout.GONE);
                    FilteredDisplay1.setVisibility(LinearLayout.VISIBLE);
                    FilteredDisplay2.setVisibility(LinearLayout.VISIBLE);
                    OrderCountDisplay.setVisibility(LinearLayout.VISIBLE);
                    ProceedButtonLayout.setVisibility(RelativeLayout.VISIBLE);
                    ItemSpinnerFunction();
                } else if (MainFilter.getSelectedItem().toString().equals("Quantity")) {
                    DeleteSecondSpinnerItem.setVisibility(RelativeLayout.GONE);
                    DeleteSecondSpinnerQuantity.setVisibility(RelativeLayout.VISIBLE);
                    FilteredDisplay1.setVisibility(LinearLayout.VISIBLE);
                    FilteredDisplay2.setVisibility(LinearLayout.VISIBLE);
                    OrderCountDisplay.setVisibility(LinearLayout.VISIBLE);
                    ProceedButtonLayout.setVisibility(RelativeLayout.VISIBLE);
                    QuantitySpinnerFunction();
                }
            }

            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private void ItemSpinnerFunction() {
        ItemFilter = (Spinner) findViewById(R.id.filter_by_item);
        List<String> items = new ArrayList<String>();
        items.add("Coffee ");
        items.add("Coffee + WC ");
        items.add("Coffee + WC + C ");
        items.add("Idli");
        items.add("Ksheera");
        items.add("Masala Dosa");
        items.add("Thuppa Dosa");
        items.add("Plain Dosa");
        items.add("Goli Baje");
        items.add("Neerulli Baje");
        items.add("Orange Juice");
        items.add("Mango Juice");
        items.add("Chikku Juice");
        items.add("Apple Juice");
        items.add("Chikku MilkShake");
        items.add("Badam MilkShake");
        items.add("Strawberry MilkShake");
        items.add("Parota");
        items.add("Chapathi");
        items.add("Black Tea");
        items.add("Tea");
        items.add("Green Tea");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ItemFilter.setAdapter(adapter);
        ItemQuery();

    }

    private void QuantitySpinnerFunction() {
        QuantityFilter = (Spinner) findViewById(R.id.filter_by_quantity);
        List<String> items = new ArrayList<String>();
        items.add("1");
        items.add("2");
        items.add("3");
        items.add("4");
        items.add("5");
        items.add("6");
        items.add("7");
        items.add("8");
        items.add("9");
        items.add("10");
        items.add("11");
        items.add("12");
        items.add("13");
        items.add("14");
        items.add("15");
        items.add("16");
        items.add("17");
        items.add("18");
        items.add("19");
        items.add("20");
        items.add("21");
        items.add("22");
        items.add("23");
        items.add("24");
        items.add("25");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        QuantityFilter.setAdapter(adapter);
        QuantityQuery();
    }

    private void ItemQuery() {
        deleteDatabase = openOrCreateDatabase("UsersAndOrders", MODE_PRIVATE, null);
        deleteDatabase.execSQL("CREATE TABLE IF NOT EXISTS orders(UserName VARCHAR,Item VARCHAR, Quantity VARCHAR , Price INTEGER);");
        if (ItemFilter.getSelectedItem().toString().equals("Goli Baje")) {
            a = deleteDatabase.rawQuery("SELECT * FROM orders WHERE Item = 'Goli Baje' AND UserName = '" + Name + "' ; ", null);
            DisplayThis();
        } else if (ItemFilter.getSelectedItem().toString().equals("Neerulli Baje")) {
            a = deleteDatabase.rawQuery("SELECT * FROM orders WHERE Item = 'Neerulli Baje' AND UserName = '" + Name + "' ; ", null);
            DisplayThis();
        } else if (ItemFilter.getSelectedItem().toString().equals("Chapathi")) {
            a = deleteDatabase.rawQuery("SELECT * FROM orders WHERE Item = 'Chapathi' AND UserName = '" + Name + "'; ", null);
            DisplayThis();
        } else if (ItemFilter.getSelectedItem().toString().equals("Coffee ")) {
            a = deleteDatabase.rawQuery("SELECT * FROM orders WHERE Item = 'Coffee ' AND UserName = '" + Name + "' ; ", null);
            DisplayThis();
        } else if (ItemFilter.getSelectedItem().toString().equals("Coffee + WC ")) {
            a = deleteDatabase.rawQuery("SELECT * FROM orders WHERE Item = 'Coffee + WC '  AND UserName = '" + Name + "'; ", null);
            DisplayThis();
        } else if (ItemFilter.getSelectedItem().toString().equals("Coffee + WC + C ")) {
            a = deleteDatabase.rawQuery("SELECT * FROM orders WHERE Item = 'Coffee + WC + C ' AND UserName = '" + Name + "' ; ", null);
            DisplayThis();
        } else if (ItemFilter.getSelectedItem().toString().equals("Idli")) {
            a = deleteDatabase.rawQuery("SELECT * FROM orders WHERE Item = 'Idli' AND UserName = '" + Name + "' ; ", null);
            DisplayThis();
        } else if (ItemFilter.getSelectedItem().toString().equals("Ksheera")) {
            a = deleteDatabase.rawQuery("SELECT * FROM orders WHERE Item = 'Ksheera' AND UserName = '" + Name + "'; ", null);
            DisplayThis();
        } else if (ItemFilter.getSelectedItem().toString().equals("Orange Juice")) {
            a = deleteDatabase.rawQuery("SELECT * FROM orders WHERE Item = 'Orange Juice' AND UserName = '" + Name + "' ; ", null);
            DisplayThis();
        } else if (ItemFilter.getSelectedItem().toString().equals("Mango Juice")) {
            a = deleteDatabase.rawQuery("SELECT * FROM orders WHERE Item = 'Mango Juice' AND UserName = '" + Name + "' ; ", null);
            DisplayThis();
        } else if (ItemFilter.getSelectedItem().toString().equals("Chikku Juice")) {
            a = deleteDatabase.rawQuery("SELECT * FROM orders WHERE Item = 'Chikku Juice' AND UserName = '" + Name + "' ; ", null);
            DisplayThis();
        } else if (ItemFilter.getSelectedItem().toString().equals("Apple Juice")) {
            a = deleteDatabase.rawQuery("SELECT * FROM orders WHERE Item = 'Apple Juice' AND UserName = '" + Name + "'; ", null);
            DisplayThis();
        } else if (ItemFilter.getSelectedItem().toString().equals("Chikku MilkShake")) {
            a = deleteDatabase.rawQuery("SELECT * FROM orders WHERE Item = 'Chikku MilkShake' AND UserName = '" + Name + "'; ", null);
            DisplayThis();
        } else if (ItemFilter.getSelectedItem().toString().equals("Badam MilkShake")) {
            a = deleteDatabase.rawQuery("SELECT * FROM orders WHERE Item = 'Badam MilkShake' AND UserName = '" + Name + "'; ", null);
            DisplayThis();
        } else if (ItemFilter.getSelectedItem().toString().equals("Strawberry MilkShake")) {
            a = deleteDatabase.rawQuery("SELECT * FROM orders WHERE Item = 'Strawberry MilkShake' AND UserName = '" + Name + "'; ", null);
            DisplayThis();
        } else if (ItemFilter.getSelectedItem().toString().equals("Masala Dosa")) {
            a = deleteDatabase.rawQuery("SELECT * FROM orders WHERE Item = 'Masala Dosa'  AND UserName = '" + Name + "'; ", null);
            DisplayThis();
        } else if (ItemFilter.getSelectedItem().toString().equals("Parota")) {
            a = deleteDatabase.rawQuery("SELECT * FROM orders WHERE Item = 'Parota' AND UserName = '" + Name + "' ; ", null);
            DisplayThis();
        } else if (ItemFilter.getSelectedItem().toString().equals("Plain Dosa")) {
            a = deleteDatabase.rawQuery("SELECT * FROM orders WHERE Item = 'Plain Dosa' AND UserName = '" + Name + "' ; ", null);
            DisplayThis();
        } else if (ItemFilter.getSelectedItem().toString().equals("Black Tea")) {
            a = deleteDatabase.rawQuery("SELECT * FROM orders WHERE Item = 'Black Tea' AND UserName = '" + Name + "' ; ", null);
            DisplayThis();
        } else if (ItemFilter.getSelectedItem().toString().equals("Tea")) {
            a = deleteDatabase.rawQuery("SELECT * FROM orders WHERE Item = 'Tea' AND UserName = '" + Name + "'; ", null);
            DisplayThis();
        } else if (ItemFilter.getSelectedItem().toString().equals("Green Tea")) {
            a = deleteDatabase.rawQuery("SELECT * FROM orders WHERE Item = 'Green Tea' AND UserName = '" + Name + "'; ", null);
            DisplayThis();
        } else if (ItemFilter.getSelectedItem().toString().equals("Thuppa Dosa")) {
            a = deleteDatabase.rawQuery("SELECT * FROM orders WHERE Item = 'Thuppa Dosa' AND UserName = '" + Name + "' ; ", null);
            DisplayThis();
        }
        ItemFilter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                if (ItemFilter.getSelectedItem().toString().equals("Goli Baje")) {
                    a = deleteDatabase.rawQuery("SELECT * FROM orders WHERE Item = 'Goli Baje' AND UserName = '" + Name + "' ; ", null);
                    DisplayThis();
                } else if (ItemFilter.getSelectedItem().toString().equals("Neerulli Baje")) {
                    a = deleteDatabase.rawQuery("SELECT * FROM orders WHERE Item = 'Neerulli Baje'AND UserName = '" + Name + "' ; ", null);
                    DisplayThis();
                } else if (ItemFilter.getSelectedItem().toString().equals("Chapathi")) {
                    a = deleteDatabase.rawQuery("SELECT * FROM orders WHERE Item = 'Chapathi' AND UserName = '" + Name + "' ; ", null);
                    DisplayThis();
                } else if (ItemFilter.getSelectedItem().toString().equals("Coffee ")) {
                    a = deleteDatabase.rawQuery("SELECT * FROM orders WHERE Item = 'Coffee ' AND UserName = '" + Name + "'; ", null);
                    DisplayThis();
                } else if (ItemFilter.getSelectedItem().toString().equals("Coffee + WC ")) {
                    a = deleteDatabase.rawQuery("SELECT * FROM orders WHERE Item = 'Coffee + WC ' AND UserName = '" + Name + "' ; ", null);
                    DisplayThis();
                } else if (ItemFilter.getSelectedItem().toString().equals("Coffee + WC + C ")) {
                    a = deleteDatabase.rawQuery("SELECT * FROM orders WHERE Item = 'Coffee + WC + C ' AND UserName = '" + Name + "' ; ", null);
                    DisplayThis();
                } else if (ItemFilter.getSelectedItem().toString().equals("Idli")) {
                    a = deleteDatabase.rawQuery("SELECT * FROM orders WHERE Item = 'Idli' AND UserName = '" + Name + "' ; ", null);
                    DisplayThis();
                } else if (ItemFilter.getSelectedItem().toString().equals("Ksheera")) {
                    a = deleteDatabase.rawQuery("SELECT * FROM orders WHERE Item = 'Ksheera' AND UserName = '" + Name + "' ; ", null);
                    DisplayThis();
                } else if (ItemFilter.getSelectedItem().toString().equals("Orange Juice")) {
                    a = deleteDatabase.rawQuery("SELECT * FROM orders WHERE Item = 'Orange Juice' AND UserName = '" + Name + "' ; ", null);
                    DisplayThis();
                } else if (ItemFilter.getSelectedItem().toString().equals("Mango Juice")) {
                    a = deleteDatabase.rawQuery("SELECT * FROM orders WHERE Item = 'Mango Juice' AND UserName = '" + Name + "'; ", null);
                    DisplayThis();
                } else if (ItemFilter.getSelectedItem().toString().equals("Chikku Juice")) {
                    a = deleteDatabase.rawQuery("SELECT * FROM orders WHERE Item = 'Chikku Juice' AND UserName = '" + Name + "' ; ", null);
                    DisplayThis();
                } else if (ItemFilter.getSelectedItem().toString().equals("Apple Juice")) {
                    a = deleteDatabase.rawQuery("SELECT * FROM orders WHERE Item = 'Apple Juice' AND UserName = '" + Name + "' ; ", null);
                    DisplayThis();
                } else if (ItemFilter.getSelectedItem().toString().equals("Chikku MilkShake")) {
                    a = deleteDatabase.rawQuery("SELECT * FROM orders WHERE Item = 'Chikku MilkShake' AND UserName = '" + Name + "' ; ", null);
                    DisplayThis();
                } else if (ItemFilter.getSelectedItem().toString().equals("Badam MilkShake")) {
                    a = deleteDatabase.rawQuery("SELECT * FROM orders WHERE Item = 'Badam MilkShake' AND UserName = '" + Name + "' ; ", null);
                    DisplayThis();
                } else if (ItemFilter.getSelectedItem().toString().equals("Strawberry MilkShake")) {
                    a = deleteDatabase.rawQuery("SELECT * FROM orders WHERE Item = 'Strawberry MilkShake' AND UserName = '" + Name + "' ; ", null);
                    DisplayThis();
                } else if (ItemFilter.getSelectedItem().toString().equals("Masala Dosa")) {
                    a = deleteDatabase.rawQuery("SELECT * FROM orders WHERE Item = 'Masala Dosa' AND UserName = '" + Name + "' ; ", null);
                    DisplayThis();
                } else if (ItemFilter.getSelectedItem().toString().equals("Parota")) {
                    a = deleteDatabase.rawQuery("SELECT * FROM orders WHERE Item = 'Parota' AND UserName = '" + Name + "' ; ", null);
                    DisplayThis();
                } else if (ItemFilter.getSelectedItem().toString().equals("Plain Dosa")) {
                    a = deleteDatabase.rawQuery("SELECT * FROM orders WHERE Item = 'Plain Dosa' AND UserName = '" + Name + "' ; ", null);
                    DisplayThis();
                } else if (ItemFilter.getSelectedItem().toString().equals("Black Tea")) {
                    a = deleteDatabase.rawQuery("SELECT * FROM orders WHERE Item = 'Black Tea' AND UserName = '" + Name + "' ; ", null);
                    DisplayThis();
                } else if (ItemFilter.getSelectedItem().toString().equals("Tea")) {
                    a = deleteDatabase.rawQuery("SELECT * FROM orders WHERE Item = 'Tea' AND UserName = '" + Name + "'; ", null);
                    DisplayThis();
                } else if (ItemFilter.getSelectedItem().toString().equals("Green Tea")) {
                    a = deleteDatabase.rawQuery("SELECT * FROM orders WHERE Item = 'Green Tea' AND UserName = '" + Name + "' ; ", null);
                    DisplayThis();
                } else if (ItemFilter.getSelectedItem().toString().equals("Thuppa Dosa")) {
                    a = deleteDatabase.rawQuery("SELECT * FROM orders WHERE Item = 'Thuppa Dosa' AND UserName = '" + Name + "' ; ", null);
                    DisplayThis();
                }
            }

            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private void QuantityQuery() {
        deleteDatabase = openOrCreateDatabase("UsersAndOrders", MODE_PRIVATE, null);
        deleteDatabase.execSQL("CREATE TABLE IF NOT EXISTS orders(UserName VARCHAR,Item VARCHAR, Quantity VARCHAR);");
        if (QuantityFilter.getSelectedItem().toString().equals("1")) {
            a = deleteDatabase.rawQuery("SELECT * FROM orders WHERE Quantity = '1' AND UserName = '" + Name + "' ; ", null);
            DisplayThis();
        } else if (QuantityFilter.getSelectedItem().toString().equals("2")) {
            a = deleteDatabase.rawQuery("SELECT * FROM orders WHERE Quantity = '2' AND UserName = '" + Name + "' ; ", null);
            DisplayThis();
        } else if (QuantityFilter.getSelectedItem().toString().equals("3")) {
            a = deleteDatabase.rawQuery("SELECT * FROM orders WHERE Quantity = '3' AND UserName = '" + Name + "' ; ", null);
            DisplayThis();
        } else if (QuantityFilter.getSelectedItem().toString().equals("4")) {
            a = deleteDatabase.rawQuery("SELECT * FROM orders WHERE Quantity = '4' AND UserName = '" + Name + "' ; ", null);
            DisplayThis();
        } else if (QuantityFilter.getSelectedItem().toString().equals("5")) {
            a = deleteDatabase.rawQuery("SELECT * FROM orders WHERE Quantity = '5' AND UserName = '" + Name + "' ; ", null);
            DisplayThis();
        } else if (QuantityFilter.getSelectedItem().toString().equals("6")) {
            a = deleteDatabase.rawQuery("SELECT * FROM orders WHERE Quantity = '6' AND UserName = '" + Name + "' ; ", null);
            DisplayThis();
        } else if (QuantityFilter.getSelectedItem().toString().equals("7")) {
            a = deleteDatabase.rawQuery("SELECT * FROM orders WHERE Quantity = '7' AND UserName = '" + Name + "' ; ", null);
            DisplayThis();
        } else if (QuantityFilter.getSelectedItem().toString().equals("8")) {
            a = deleteDatabase.rawQuery("SELECT * FROM orders WHERE Quantity = '8' AND UserName = '" + Name + "' ; ", null);
            DisplayThis();
        } else if (QuantityFilter.getSelectedItem().toString().equals("9")) {
            a = deleteDatabase.rawQuery("SELECT * FROM orders WHERE Quantity = '9' AND UserName = '" + Name + "' ; ", null);
            DisplayThis();
        } else if (QuantityFilter.getSelectedItem().toString().equals("10")) {
            a = deleteDatabase.rawQuery("SELECT * FROM orders WHERE Quantity = '10' AND UserName = '" + Name + "' ; ", null);
            DisplayThis();
        } else if (QuantityFilter.getSelectedItem().toString().equals("11")) {
            a = deleteDatabase.rawQuery("SELECT * FROM orders WHERE Quantity = '11' AND UserName = '" + Name + "' ; ", null);
            DisplayThis();
        } else if (QuantityFilter.getSelectedItem().toString().equals("12")) {
            a = deleteDatabase.rawQuery("SELECT * FROM orders WHERE Quantity = '12' AND UserName = '" + Name + "' ; ", null);
            DisplayThis();
        } else if (QuantityFilter.getSelectedItem().toString().equals("13")) {
            a = deleteDatabase.rawQuery("SELECT * FROM orders WHERE Quantity = '13' AND UserName = '" + Name + "' ; ", null);
            DisplayThis();
        } else if (QuantityFilter.getSelectedItem().toString().equals("14")) {
            a = deleteDatabase.rawQuery("SELECT * FROM orders WHERE Quantity = '14' AND UserName = '" + Name + "' ; ", null);
            DisplayThis();
        } else if (QuantityFilter.getSelectedItem().toString().equals("15")) {
            a = deleteDatabase.rawQuery("SELECT * FROM orders WHERE Quantity = '15' AND UserName = '" + Name + "' ; ", null);
            DisplayThis();
        } else if (QuantityFilter.getSelectedItem().toString().equals("16")) {
            a = deleteDatabase.rawQuery("SELECT * FROM orders WHERE Quantity = '16' AND UserName = '" + Name + "' ; ", null);
            DisplayThis();
        } else if (QuantityFilter.getSelectedItem().toString().equals("17")) {
            a = deleteDatabase.rawQuery("SELECT * FROM orders WHERE Quantity = '17' AND UserName = '" + Name + "' ; ", null);
            DisplayThis();
        } else if (QuantityFilter.getSelectedItem().toString().equals("18")) {
            a = deleteDatabase.rawQuery("SELECT * FROM orders WHERE Quantity = '18' AND UserName = '" + Name + "' ; ", null);
            DisplayThis();
        } else if (QuantityFilter.getSelectedItem().toString().equals("19")) {
            a = deleteDatabase.rawQuery("SELECT * FROM orders WHERE Quantity = '19' AND UserName = '" + Name + "' ; ", null);
            DisplayThis();
        } else if (QuantityFilter.getSelectedItem().toString().equals("20")) {
            a = deleteDatabase.rawQuery("SELECT * FROM orders WHERE Quantity = '20' AND UserName = '" + Name + "' ; ", null);
            DisplayThis();
        } else if (QuantityFilter.getSelectedItem().toString().equals("21")) {
            a = deleteDatabase.rawQuery("SELECT * FROM orders WHERE Quantity = '21' AND UserName = '" + Name + "' ; ", null);
            DisplayThis();
        } else if (QuantityFilter.getSelectedItem().toString().equals("22")) {
            a = deleteDatabase.rawQuery("SELECT * FROM orders WHERE Quantity = '22' AND UserName = '" + Name + "' ; ", null);
            DisplayThis();
        } else if (QuantityFilter.getSelectedItem().toString().equals("23")) {
            a = deleteDatabase.rawQuery("SELECT * FROM orders WHERE Quantity = '23' AND UserName = '" + Name + "' ; ", null);
            DisplayThis();
        } else if (QuantityFilter.getSelectedItem().toString().equals("24")) {
            a = deleteDatabase.rawQuery("SELECT * FROM orders WHERE Quantity = '24' AND UserName = '" + Name + "' ; ", null);
            DisplayThis();
        } else if (QuantityFilter.getSelectedItem().toString().equals("25")) {
            a = deleteDatabase.rawQuery("SELECT * FROM orders WHERE Quantity = '25' AND UserName = '" + Name + "' ; ", null);
            DisplayThis();
        }
        QuantityFilter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                if (QuantityFilter.getSelectedItem().toString().equals("1")) {
                    a = deleteDatabase.rawQuery("SELECT * FROM orders WHERE Quantity = '1' AND UserName = '" + Name + "' ; ", null);
                    DisplayThis();
                } else if (QuantityFilter.getSelectedItem().toString().equals("2")) {
                    a = deleteDatabase.rawQuery("SELECT * FROM orders WHERE Quantity = '2' AND UserName = '" + Name + "' ; ", null);
                    DisplayThis();
                } else if (QuantityFilter.getSelectedItem().toString().equals("3")) {
                    a = deleteDatabase.rawQuery("SELECT * FROM orders WHERE Quantity = '3' AND UserName = '" + Name + "' ; ", null);
                    DisplayThis();
                } else if (QuantityFilter.getSelectedItem().toString().equals("4")) {
                    a = deleteDatabase.rawQuery("SELECT * FROM orders WHERE Quantity = '4' AND UserName = '" + Name + "' ; ", null);
                    DisplayThis();
                } else if (QuantityFilter.getSelectedItem().toString().equals("5")) {
                    a = deleteDatabase.rawQuery("SELECT * FROM orders WHERE Quantity = '5' AND UserName = '" + Name + "' ; ", null);
                    DisplayThis();
                } else if (QuantityFilter.getSelectedItem().toString().equals("6")) {
                    a = deleteDatabase.rawQuery("SELECT * FROM orders WHERE Quantity = '6' AND UserName = '" + Name + "' ; ", null);
                    DisplayThis();
                } else if (QuantityFilter.getSelectedItem().toString().equals("7")) {
                    a = deleteDatabase.rawQuery("SELECT * FROM orders WHERE Quantity = '7' AND UserName = '" + Name + "' ; ", null);
                    DisplayThis();
                } else if (QuantityFilter.getSelectedItem().toString().equals("8")) {
                    a = deleteDatabase.rawQuery("SELECT * FROM orders WHERE Quantity = '8' AND UserName = '" + Name + "' ; ", null);
                    DisplayThis();
                } else if (QuantityFilter.getSelectedItem().toString().equals("9")) {
                    a = deleteDatabase.rawQuery("SELECT * FROM orders WHERE Quantity = '9' AND UserName = '" + Name + "' ; ", null);
                    DisplayThis();
                } else if (QuantityFilter.getSelectedItem().toString().equals("10")) {
                    a = deleteDatabase.rawQuery("SELECT * FROM orders WHERE Quantity = '10' AND UserName = '" + Name + "' ; ", null);
                    DisplayThis();
                } else if (QuantityFilter.getSelectedItem().toString().equals("11")) {
                    a = deleteDatabase.rawQuery("SELECT * FROM orders WHERE Quantity = '11' AND UserName = '" + Name + "' ; ", null);
                    DisplayThis();
                } else if (QuantityFilter.getSelectedItem().toString().equals("12")) {
                    a = deleteDatabase.rawQuery("SELECT * FROM orders WHERE Quantity = '12' AND UserName = '" + Name + "' ; ", null);
                    DisplayThis();
                } else if (QuantityFilter.getSelectedItem().toString().equals("13")) {
                    a = deleteDatabase.rawQuery("SELECT * FROM orders WHERE Quantity = '13' AND UserName = '" + Name + "' ; ", null);
                    DisplayThis();
                } else if (QuantityFilter.getSelectedItem().toString().equals("14")) {
                    a = deleteDatabase.rawQuery("SELECT * FROM orders WHERE Quantity = '14' AND UserName = '" + Name + "' ; ", null);
                    DisplayThis();
                } else if (QuantityFilter.getSelectedItem().toString().equals("15")) {
                    a = deleteDatabase.rawQuery("SELECT * FROM orders WHERE Quantity = '15' AND UserName = '" + Name + "' ; ", null);
                    DisplayThis();
                } else if (QuantityFilter.getSelectedItem().toString().equals("16")) {
                    a = deleteDatabase.rawQuery("SELECT * FROM orders WHERE Quantity = '16' AND UserName = '" + Name + "' ; ", null);
                    DisplayThis();
                } else if (QuantityFilter.getSelectedItem().toString().equals("17")) {
                    a = deleteDatabase.rawQuery("SELECT * FROM orders WHERE Quantity = '17' AND UserName = '" + Name + "' ; ", null);
                    DisplayThis();
                } else if (QuantityFilter.getSelectedItem().toString().equals("18")) {
                    a = deleteDatabase.rawQuery("SELECT * FROM orders WHERE Quantity = '18' AND UserName = '" + Name + "' ; ", null);
                    DisplayThis();
                } else if (QuantityFilter.getSelectedItem().toString().equals("19")) {
                    a = deleteDatabase.rawQuery("SELECT * FROM orders WHERE Quantity = '19' AND UserName = '" + Name + "' ; ", null);
                    DisplayThis();
                } else if (QuantityFilter.getSelectedItem().toString().equals("20")) {
                    a = deleteDatabase.rawQuery("SELECT * FROM orders WHERE Quantity = '20' AND UserName = '" + Name + "' ; ", null);
                    DisplayThis();
                } else if (QuantityFilter.getSelectedItem().toString().equals("21")) {
                    a = deleteDatabase.rawQuery("SELECT * FROM orders WHERE Quantity = '21' AND UserName = '" + Name + "' ; ", null);
                    DisplayThis();
                } else if (QuantityFilter.getSelectedItem().toString().equals("22")) {
                    a = deleteDatabase.rawQuery("SELECT * FROM orders WHERE Quantity = '22' AND UserName = '" + Name + "' ; ", null);
                    DisplayThis();
                } else if (QuantityFilter.getSelectedItem().toString().equals("23")) {
                    a = deleteDatabase.rawQuery("SELECT * FROM orders WHERE Quantity = '23' AND UserName = '" + Name + "' ; ", null);
                    DisplayThis();
                } else if (QuantityFilter.getSelectedItem().toString().equals("24")) {
                    a = deleteDatabase.rawQuery("SELECT * FROM orders WHERE Quantity = '24' AND UserName = '" + Name + "' ; ", null);
                    DisplayThis();
                } else if (QuantityFilter.getSelectedItem().toString().equals("25")) {
                    a = deleteDatabase.rawQuery("SELECT * FROM orders WHERE Quantity = '25' AND UserName = '" + Name + "' ; ", null);
                    DisplayThis();
                }
            }

            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void DisplayThis() {
        String orderCount;
        if (a.getCount() == 0) {
            orderCount = "There are no Items to Delete !!";
            FilteredDisplay1.setVisibility(LinearLayout.GONE);
            FilteredDisplay2.setVisibility(LinearLayout.GONE);
            ProceedButtonLayout.setVisibility(RelativeLayout.GONE);

        } else {
            FilteredDisplay1.setVisibility(LinearLayout.VISIBLE);
            FilteredDisplay2.setVisibility(LinearLayout.VISIBLE);
            ProceedButtonLayout.setVisibility(RelativeLayout.VISIBLE);
            orderCount = " Number of Orders : " + a.getCount() + "\n";
            String item = "";
            String quantity = "";
            String price = "";
            while (a.moveToNext()) {
                item += a.getString(1) + "\n";
                quantity += a.getString(2) + "\n";
                price += a.getString(3) + "\n";
            }
            orderCount += "Total Price: $" + getTotalDeletePrice();
            TextView nameMessage = (TextView) findViewById(R.id.filter_content_mine1);
            nameMessage.setText(item);
            TextView quantityValues = (TextView) findViewById(R.id.filter_content_mine2);
            quantityValues.setText(quantity);
            TextView priceValues = (TextView) findViewById(R.id.filter_content_mine3);
            priceValues.setText(price);
        }
        TextView countMessage = (TextView) findViewById(R.id.order_count_display);
        countMessage.setText(orderCount);
    }

    private int getTotalDeletePrice() {
        a.moveToFirst();
        int total = a.getInt(3);
        while (a.moveToNext()) {
            total += a.getInt(3);
        }
        a.close();
        return total;
    }

    public void DeleteConfirm(View view) {
        Spinner MainFilterSpinner = (Spinner) findViewById(R.id.filter_by);
        Spinner ItemFilterSpinner = (Spinner) findViewById(R.id.filter_by_item);
        Spinner QuantityFilterSpinner = (Spinner) findViewById(R.id.filter_by_quantity);
        deleteDatabase = openOrCreateDatabase("UsersAndOrders", MODE_PRIVATE, null);
        deleteDatabase.execSQL("CREATE TABLE IF NOT EXISTS orders(UserName VARCHAR,Item VARCHAR, Quantity VARCHAR , Price INTEGER);");
        if (String.valueOf(MainFilterSpinner.getSelectedItem().toString()).equals("Items")) {
            String DeleteItemList = String.valueOf(ItemFilterSpinner.getSelectedItem().toString());
            deleteDatabase.execSQL(" DELETE FROM orders WHERE Item = '" + DeleteItemList + "' AND  UserName = '" + Name + "' ; ");
            Toast.makeText(getApplicationContext(), DeleteItemList + " Items deleted Successfully!!", Toast.LENGTH_SHORT).show();
        } else if (String.valueOf(MainFilterSpinner.getSelectedItem().toString()).equals("Quantity")) {
            String DeleteQuantityList = String.valueOf(QuantityFilterSpinner.getSelectedItem().toString());
            deleteDatabase.execSQL(" DELETE FROM orders WHERE Quantity = '" + DeleteQuantityList + "' AND UserName = '" + Name + "' ; ");
            Toast.makeText(getApplicationContext(), "' " + DeleteQuantityList + " '" + "Numbered quantity Items deleted successfully!!", Toast.LENGTH_SHORT).show();
        }
        FirstLayoutOfThePage();
        Cursor zeroCheck = OrderDataBase.rawQuery("SELECT * FROM orders WHERE UserName = '" + Name + "' ; ", null);
        if (zeroCheck.getCount() != 0) {
            DeleteFunction(view);
        }
        zeroCheck.close();
    }
}





