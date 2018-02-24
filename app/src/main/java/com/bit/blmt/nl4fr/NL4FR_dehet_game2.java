package com.bit.blmt.nl4fr;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class NL4FR_dehet_game2 extends AppCompatActivity {
    //PROGRESSIVE MODE
    //RULES :
    // - 100 % : ADD 10 WORDS
    // - 90% : ADD 3 WORDS
    // - 80 % : ADD 1 WORDS
    // - <80 % : DON'T PROGRESS

    ArrayList woorden = new ArrayList();
    ArrayList lidwoorden = new ArrayList();
    TextView woord = null;
    Button btn_de = null;
    Button btn_het = null;
    int count = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nl4_fr_dehet_game2);
        System.out.println("Storage State : " + Environment.getExternalStorageState());

        //set activity elements
        woord = (TextView)findViewById(R.id.tv_woord);
        btn_het = (Button)findViewById(R.id.btn_het);
        btn_de = (Button)findViewById(R.id.btn_de);
        //Offline mode - means not registered user
         if(LoadFileIfExist())
         {
             //downloadJSON("http://quizart.be/lidwoord/getdata_dehet_game2_firstplay.php");
             btn_de.setVisibility(View.VISIBLE);
             btn_het.setVisibility(View.VISIBLE);
         }
         else
         {
             //First time the game is started
             startActivity(new Intent(NL4FR_dehet_game2.this, NL4FR_dehet_game2_info_popup.class));
         }

    }

    private boolean LoadFileIfExist()
    {
        File file = new File("localdb_dehet_progressive.txt");
        if(file.exists())
        {
            try {
                FileWriter writer = new FileWriter(file);
                BufferedReader br = new BufferedReader(new FileReader(file));
                String line;
                String data[];
                int count = 0;
                while ((line = br.readLine()) != null) {
                    if (line != "#END#") {
                        count++;
                        data = line.split(";");
                        lidwoorden.add(data[0]);
                        woorden.add(data[1]);
                    }
                }
                br.close();
            }
            catch (IOException e)
            {
                //ERROR READING FILE
            }
            return true;

        }
        else
        {
            //First Load 10 Words from database/

            try {
                FileWriter writer = new FileWriter(file);
                BufferedReader br = new BufferedReader(new FileReader(file));
                String line;
                String data[];
                int count = 0;
                while ((line = br.readLine()) != null) {
                    if (line != "#END#") {
                        count++;
                        data = line.split(";");
                        lidwoorden.add(data[0]);
                        woorden.add(data[1]);
                    }
                }
                br.close();
            }
            catch (IOException e)
            {
                //ERROR READING FILE
            }
            return false;
        }
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
                System.out.println("Task executed");
                try {
                    FillArrays(s);
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
        Toast.makeText(NL4FR_dehet_game2.this, "Count " + count + " - Max " + woorden.size(),Toast.LENGTH_LONG).show();
        //woord.setText(woorden.get(count).toString());
        if (count <= woorden.size()) {
            switch (view.getId()) {
                case 2131558518:
                    System.out.println("DE");
                    if(lidwoorden.get(count).toString().toLowerCase().equals("de")) {
                        ChangeWoord();
                        Toast.makeText(NL4FR_dehet_game2.this, "Correct",Toast.LENGTH_LONG).show();
                    }
                    break;
                case 2131558519:
                    System.out.println("HET");
                    if(lidwoorden.get(count).toString().toLowerCase().equals("het")) {
                        ChangeWoord();
                        Toast.makeText(NL4FR_dehet_game2.this, "Correct",Toast.LENGTH_LONG).show();
                    }
                    break;
                default:
                    System.out.println("ID BUTTON : " + view.getId());
                    break;
            }
        }
        else
        {
            ChangeWoord();
            Toast.makeText(NL4FR_dehet_game2.this, "Max Reached " + woorden.size(),Toast.LENGTH_LONG).show();
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
