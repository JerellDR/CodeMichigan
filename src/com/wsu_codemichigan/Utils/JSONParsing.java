package com.wsu_codemichigan.Utils;

import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import com.google.gson.Gson;
import com.wsu_codemichigan.Google.GoogleStreetViewActivity;
import com.wsu_codemichigan.MainActivity;
import com.wsu_codemichigan.R;
import com.wsu_codemichigan.parcelables.CustomList;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by Mrgadgetz
 * Date: 8/14/13
 * Time: 2:15 PM
 */
public class JSONParsing extends AsyncTask<Void, Void, Void> {
    private String returnJson = "";
    private CustomList[] cust;
    MainActivity activity;
    //ArrayList list;
    public static StableArrayAdapter adapter;

    public JSONParsing(MainActivity activity) {
        this.activity = activity;

    }

    @Override
    protected Void doInBackground(Void... urls) {


        try {
            returnJson = getJSONFromUrl(activity.url);
            Gson gson = new Gson();
            cust = gson.fromJson(returnJson, CustomList[].class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        activity.listView = (ListView) activity.findViewById(R.id.listView1);
        updateAdapter();
    }

    public String getJSONFromUrl(String url) throws IOException {
        String requestString = "";
        HttpClient client = new DefaultHttpClient();
        HttpGet request = new HttpGet(url);

        try {
            HttpResponse response = client.execute(request);
            InputStream content = response.getEntity().getContent();
            BufferedReader rd = new BufferedReader(
                    new InputStreamReader(content));
            StringBuilder sb = new StringBuilder();
            String line = "";

            while ((line = rd.readLine()) != null) {
                sb.append(line);
            }

            content.close();
            requestString = sb.toString();

        } catch (IOException e) {
            Log.e("Buffer Error", "Error converting result " + e.toString());
        }
        returnJson = requestString;
        return requestString;
    }

    public void updateAdapter() {
        for (int i = 0; i < cust.length; i++) {
            activity.list.add(cust[i].management_company);
            //activity.list.add(cust[i]);
        }

        activity.adapter = new StableArrayAdapter(activity, R.layout.activity_main, activity.list);
        activity.listView.setAdapter(activity.adapter);
        setClickListener();

    }

    private void setClickListener() {
        activity.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, final View view
                    , int position, long id) {

                final String item = (String) adapterView.getItemAtPosition(position);
                Intent intent = new Intent(activity, GoogleStreetViewActivity.class);
                //TODO store longituede and latitude in extras bundle
                activity.startActivity(intent);
            }
        });


    }

}