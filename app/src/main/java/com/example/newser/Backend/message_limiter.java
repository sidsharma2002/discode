package com.example.newser.Backend;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.TextView;

import androidx.preference.PreferenceManager;

public class message_limiter {
Context context;
TextView textView;

    public message_limiter(Context context, TextView textView) {
        this.context = context;
        this.textView= textView;
    }

     public int getmessagelimit(){
       SharedPreferences getShared = PreferenceManager.getDefaultSharedPreferences( context);
       return  getShared.getInt("message_limit", 5);
    }

    public void updatemessagelimit(int messagelimit){
        SharedPreferences updateShared = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = updateShared.edit();
        editor.putInt("message_limit", messagelimit);
        editor.apply();
        textView.setText("Limits left: " + getmessagelimit());
    }
}
