package com.covid19information;

import android.app.Activity;
import android.app.Dialog;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Than_Phyo on 5/13/2008.
 */
public class OnlineNotice {
    private Activity activity;
    private String json_url;
    private int versionCode = 0;
    private String download_link = null;

    public OnlineNotice(Activity activity, String json_url){
        this.activity = activity;
        this.json_url = json_url;
        check();
    }

    private void check() {
        new AsyncTask<Void, Void, String>(){

            @Override
            protected String doInBackground(Void... params) {
                try{
                    return fetch(json_url);
                }catch (Exception e){
                    return e.getMessage();
                }
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                if(s!=null){
                    letCheck(s);
                }
            }
        }.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    private void letCheck(String response) {
        if(response!=null){
            try {
                JSONObject obj = new JSONObject(response);
                String title = obj.getString("title");
                String msg = obj.getString("message");
                download_link = obj.getString("link");
                boolean force = obj.getBoolean("force");
                versionCode = obj.getInt("versionCode");

                PackageManager pm = activity.getPackageManager();
                PackageInfo packageInfo;
                int currentVersion = 0;
                try {
                    packageInfo = pm.getPackageInfo(activity.getPackageName(), 0);
                    currentVersion = packageInfo.versionCode;
                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                }

                if(versionCode > currentVersion){
                    letUpdate(title, msg, force);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private void letUpdate(String title, String msg, boolean force) {
        final Dialog dilog = new Dialog(activity);
        dilog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dilog.setContentView(R.layout.notice_dialog);
        dilog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dilog.show();

        TextView noticeTitle = (TextView)dilog.findViewById(R.id.noticeTitle);
        TextView mesg = (TextView)dilog.findViewById(R.id.mesg);
        Button btnOk = (Button)dilog.findViewById(R.id.btnOk);
        btnOk.setBackgroundColor(Color.TRANSPARENT);

        noticeTitle.setText(title);
        mesg.setText(msg);

        if(!force){
            btnOk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dilog.dismiss();
                }
            });
        }
    }

    private String fetch(String link) {
        String result = "";
        try{
            URL url = new URL(link);
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            connection.setRequestMethod("GET");
            int ResponseCode = connection.getResponseCode();
            if(ResponseCode == HttpURLConnection.HTTP_OK){
                InputStream inputStream = connection.getInputStream();
                ByteArrayOutputStream ba = new ByteArrayOutputStream();
                byte[] buffer = new byte[1024];
                int length;
                while ((length = inputStream.read(buffer)) != -1){
                    ba.write(buffer, 0, length);
                }
                ba.close();
                result = ba.toString("UTF-8");
            }
        } catch (Exception e) {
           result = e.getMessage();
        }
        return result;
    }
}
