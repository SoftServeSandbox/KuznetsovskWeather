package com.kuznweather;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.TextView;

import com.kuznweather.administrator.kuznweather.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

/**
 * Created by Administrator on 8/15/2015.
 */
public class URLLoader extends AsyncTask <String, Void, String>{

    private MainActivity m_act = null;
    private StringBuilder content;
    private String temp="";
    private String cloud="";
    private String pressure="";

    public URLLoader(MainActivity act)
    {
        Log.d("URLLoader", "URLLoader");
       m_act = act;
    }

    @Override
    protected void onPreExecute() {}

    @Override
    protected String doInBackground(String... params)
    {
        Log.d("URLLoader", "doInBackground");
        return loadUrl();
    }

    public String loadUrl()
    {
        Log.d("URLLoader", "doInBackground");
        content = new StringBuilder();
        try {
            URL url = new URL("http://api.openweathermap.org/data/2.5/weather?id=703464&units=metric&lang=ua");
            BufferedReader bufRead = new BufferedReader(new InputStreamReader(url.openConnection().getInputStream()));
            String line;
            while ((line = bufRead.readLine()) != null)
            {
                content.append(line + "\n");
            }
            bufRead.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        try {
            JSONObject jsonObject = new JSONObject(content.toString());
            JSONObject main = jsonObject.getJSONObject("main");
            temp = main.getString("temp");
            pressure = main.getString("pressure");
            JSONArray arr = jsonObject.getJSONArray("weather");
            main = arr.getJSONObject(0);
            cloud = main.getString("description");
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }
        return content.toString();
    }

    @Override
    protected void onPostExecute(String result) {

        TextView tvPressure = (TextView) m_act.findViewById(R.id.IDPressure);
        tvPressure.setText(pressure);

        TextView tvClouds = (TextView) m_act.findViewById(R.id.IDClouds);
        tvClouds.setText(cloud);

        TextView tvTempLabel = (TextView) m_act.findViewById(R.id.IDTemp);
        tvTempLabel.setText(temp);
    }
}
