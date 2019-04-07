/**
 * Created by srivara on 13-08-2017.
 */

package com.example.andoid.dinord;

public class word {

    private String mItemName;

    private String mPriceTag;

    public word(String itemName , String priceTag){
        mItemName = itemName ;
        mPriceTag = priceTag;
    }

    public  String getItemName(){
        return mItemName;

    }
    public String getPriceTag(){
        return mPriceTag;
    }
}
