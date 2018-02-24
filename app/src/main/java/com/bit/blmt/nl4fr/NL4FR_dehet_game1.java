package com.bit.blmt.nl4fr;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class NL4FR_dehet_game1 extends AppCompatActivity {
    // SURVIVAL MODE
    ListView listView;
    ArrayList woorden = new ArrayList();
    ArrayList lidwoorden = new ArrayList();
    TextView woord = null;
    int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        System.out.println("Entering class game1");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nl4_fr_dehet_game1);
        System.out.println("Entering game");
        woord = (TextView)findViewById(R.id.tv_woord);
        System.out.println("Start Get Data");
        downloadJSON("http://quizart.be/lidwoord/getdata.php");
        System.out.println("Get Data Ok");

    }

    private void downloadJSON(final String urlWebService) {
        class DownloadJSON extends AsyncTask<Void, Void, String> {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }


            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                woord.setText("Text ready");
                System.out.println("Task executed");
                try {
                    FillArrays(s);
                    woord.setText(woorden.get(count).toString());
                } catch (Exception e)
                {

                }


            }

            @Override
            protected String doInBackground(Void... voids) {
                try {
                    URL url = new URL(urlWebService);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    StringBuilder sb = new StringBuilder();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
                    String json;
                    while ((json = bufferedReader.readLine()) != null) {
                        sb.append(json + "\n");
                    }
                    return sb.toString().trim();

                } catch (Exception e) {
                    System.out.println("Error : " + e.toString());
                    return null;
                }
            }
        }
        DownloadJSON getJSON = new DownloadJSON();

        getJSON.execute();
    }

    private void FillArrays(String json) throws JSONException {
        JSONArray jsonArray = new JSONArray(json);


        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject obj = jsonArray.getJSONObject(i);
            lidwoorden.add(obj.getString("LIDWOORD").trim());
            woorden.add(obj.getString("WOORD").trim());
            //stocks[i] = obj.getString("Lidword") + " " + obj.getString("Word");
        }

    }

    public void CheckAnswer(View view)
    {
        System.out.println("view : " + view.getId());
        Toast.makeText(NL4FR_dehet_game1.this, "Count " + count + " - Max " + woorden.size(),Toast.LENGTH_LONG).show();
        //woord.setText(woorden.get(count).toString());
        if (count <= woorden.size()) {
            switch (view.getId()) {
                case 2131558518:
                    System.out.println("DE");
                    if(lidwoorden.get(count).toString().toLowerCase().equals("de")) {
                        ChangeWoord();
                        Toast.makeText(NL4FR_dehet_game1.this, "Correct",Toast.LENGTH_LONG).show();
                    }
                    break;
                case 2131558519:
                    System.out.println("HET");
                    if(lidwoorden.get(count).toString().toLowerCase().equals("het")) {
                        ChangeWoord();
                        Toast.makeText(NL4FR_dehet_game1.this, "Correct",Toast.LENGTH_LONG).show();
                    }
                    break;
            }
        }
        else
        {
            ChangeWoord();
            Toast.makeText(NL4FR_dehet_game1.this, "Max Reached " + woorden.size(),Toast.LENGTH_LONG).show();
        }
    }

    public void ChangeWoord()
    {
        if(count < (woorden.size() - 1)) {
            count++;
            woord.setText(woorden.get(count).toString());
        }
        else
        {
            count = 0;
            woord.setText(woorden.get(count).toString());
        }
    }

}