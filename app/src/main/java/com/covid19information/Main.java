package com.covid19information;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


public class Main extends Activity {
    public static TextView data1, data2, data3, data1small, data2small, data3small, SountKyi, datKhwalpee, time;
    TextView title1, title2, title3, title4, title5, title6, infoText, devFb;
    Button btnInfo, ok;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        new OnlineNotice(this, "http://thanwaiphyo.000webhostapp.com/Update/update.json");

        ActionBar ab = getActionBar();
        ab.hide();

        MyReceiver myReceiver = new MyReceiver();
        IntentFilter intenFilter = new IntentFilter();
        intenFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        registerReceiver(myReceiver,intenFilter);


        btnInfo = (Button)findViewById(R.id.btnInfo);

        data1 = (TextView)findViewById(R.id.tvResult1);
        data2 = (TextView)findViewById(R.id.tvResult2);
        data3 = (TextView)findViewById(R.id.tvResult3);
        data1small = (TextView)findViewById(R.id.tvResult1small);
        data2small = (TextView)findViewById(R.id.tvResult2small);
        data3small = (TextView)findViewById(R.id.tvResult3small);
        SountKyi = (TextView)findViewById(R.id.sountkyi);
        datKhwalpee = (TextView)findViewById(R.id.datkhwalpee);
        time = (TextView)findViewById(R.id.time);

        title1 = (TextView)findViewById(R.id.title1);
        title2 = (TextView)findViewById(R.id.title2);
        title3 = (TextView)findViewById(R.id.title3);
        title4 = (TextView)findViewById(R.id.title4);
        title5 = (TextView)findViewById(R.id.title5);
        title6 = (TextView)findViewById(R.id.title6);
        infoText = (TextView)findViewById(R.id.infoText);

        font();

        if(!NetTest()){
            Toast.makeText(Main.this, "Please Open Your Internet Connection!", Toast.LENGTH_LONG).show();
        }else{
            FetchData process = new FetchData();
            process.execute();
        }

        btnInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(Main.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.info_dialog);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();

                ok = (Button) dialog.findViewById(R.id.ok);
                ok.setBackgroundColor(Color.TRANSPARENT);
                ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });

                devFb = (TextView)dialog.findViewById(R.id.devFb);
                devFb.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(OpenMyProfile(Main.this, "fb://profile/100010841646874", "https://mobile.facebook.com/profile.php?id=100010841646874"));
                    }
                });
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if(!NetTest()){
            Toast.makeText(Main.this, "Please Open Your Internet Connection!", Toast.LENGTH_LONG).show();
        }else{
            new OnlineNotice(this, "http://thanwaiphyo.000webhostapp.com/Update/update.json");
            FetchData process = new FetchData();
            process.execute();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(!NetTest()){
            Toast.makeText(Main.this, "Please Open Your Internet Connection!", Toast.LENGTH_LONG).show();
        }else{
            FetchData process = new FetchData();
            process.execute();
        }
    }

    private boolean NetTest() {
        ConnectivityManager cm = (ConnectivityManager)this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo nInfo = cm.getActiveNetworkInfo();
        return nInfo != null;
    }

    public static Intent OpenMyProfile(Context context, String uri, String uriBrowser){
        try{
            context.getPackageManager().getPackageInfo("com.facebook.katana", 0);
            return new Intent("android.intent.action.VIEW", Uri.parse(uri));
        } catch (Exception e) {
            return new Intent("android.intent.action.VIEW", Uri.parse(uriBrowser));
        }
    }

    private void font() {
        Typeface tf = Typeface.createFromAsset(getAssets(),"font.ttf");

        data1.setTypeface(tf);
        data2.setTypeface(tf);
        data3.setTypeface(tf);
        data1small.setTypeface(tf);
        data2small.setTypeface(tf);
        data3small.setTypeface(tf);
        SountKyi.setTypeface(tf);
        datKhwalpee.setTypeface(tf);
        time.setTypeface(tf);

        title1.setTypeface(tf);
        title2.setTypeface(tf);
        title3.setTypeface(tf);
        title4.setTypeface(tf);
        title5.setTypeface(tf);
        title6.setTypeface(tf);
        infoText.setTypeface(tf);

        btnInfo.setTypeface(tf);
    }


}

