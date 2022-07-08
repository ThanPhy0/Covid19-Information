package com.covid19information;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

/**
 * Created by Than_Phyo on 5/11/2008.
 */
public class MyReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        ConnectivityManager cm = (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo nInfo = cm.getActiveNetworkInfo();
        if(nInfo != null){
            if(nInfo.getType()==ConnectivityManager.TYPE_MOBILE){
                Toast.makeText(context,"Refreshed.", Toast.LENGTH_LONG).show();
                FetchData process = new FetchData();
                process.execute();
            }
        }else{
            Toast.makeText(context,"Please Open Your Internet Connection!", Toast.LENGTH_LONG).show();
        }

    }
}
