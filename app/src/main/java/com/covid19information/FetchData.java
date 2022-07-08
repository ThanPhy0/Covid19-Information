package com.covid19information;


import android.os.AsyncTask;
import android.view.View;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Than Phyo on 4/26/2020.
 */
public class FetchData extends AsyncTask<Void,Void,Void> {
    String data = "";
    String conFirm = "";
    String death = "";
    String Recovered = "";
    String conFirmsmall = "";
    String deathSmall = "";
    String RecoveredSmall = "";
    String sountKyi = "";
    String datKhwal = "";
    String time = "";
    int singleParsed;
    String conf, died, recover, confPlus, diedPlus, recoverPlus, soutky, datKhw, achain ;

    @Override
    protected Void doInBackground(Void... voids) {
        try {
            URL url = new URL("https://mm-mpxteam.net/nso/covid19/mm.json");
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            InputStream inputStream = httpURLConnection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line ="";
            while (line != null){
                line = bufferedReader.readLine();
                data = data + line;
            }
            JSONObject JO = new JSONObject(data);
            conf = JO.optString("confirm");
            died = JO.optString("deaths");
            recover = JO.optString("recovered");
            confPlus = JO.optString("newconfirm");
            diedPlus = JO.optString("newdeaths");
            recoverPlus = JO.optString("newrecovered");
            soutky = JO.optString("totalpui");
            datKhw = JO.optString("totaltested");
            achain = JO.optString("dt");

            conFirm = conFirm + conf ;
            death = death + died ;
            Recovered = Recovered + recover ;
            conFirmsmall = conFirmsmall + "+" + confPlus;
            deathSmall = deathSmall + "+" + diedPlus;
            RecoveredSmall = RecoveredSmall + "+" + recoverPlus;
            sountKyi = sountKyi +  soutky;
            datKhwal = datKhwal + datKhw;
            time = time + achain;

        }catch (MalformedURLException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        Main.data1.setText(this.conFirm);
        Main.data2.setText(this.death);
        Main.data3.setText(this.Recovered);

        if(confPlus.contentEquals("0")){
            Main.data1small.setVisibility(View.INVISIBLE);
        }
        if(diedPlus.contentEquals("0")){
            Main.data2small.setVisibility(View.INVISIBLE);
        }
        if(recoverPlus.contentEquals("0")){
                Main.data3small.setVisibility(View.INVISIBLE);
        }

        Main.data1small.setText(this.conFirmsmall);
        Main.data2small.setText(this.deathSmall);
        Main.data3small.setText(this.RecoveredSmall);

        Main.SountKyi.setText(this.sountKyi);
        Main.datKhwalpee.setText(this.datKhwal);
        Main.time.setText(this.time);
    }
}
