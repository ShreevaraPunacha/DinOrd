package com.example.andoid.dinord;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by srivara on 13-08-2017.
 */

public class WordAdapter extends ArrayAdapter<word>{


public WordAdapter(Activity context , ArrayList<word> words ){
    super(context , 0 ,words);
}

    @Nullable
    @Override

    public View getView(int position ,@Nullable View convertView , @NonNull ViewGroup parent ){
    View listItemView = convertView;
    if (listItemView == null){
        listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_file , parent , false);
    }
    word currentWord = getItem(position);

    TextView itemTextView = (TextView) listItemView.findViewById(R.id.itemName);
    itemTextView.setText(currentWord.getItemName());
    TextView priceTextView = (TextView) listItemView.findViewById(R.id.priceTag);
    priceTextView.setText(currentWord.getPriceTag());
    return listItemView;

}
}
