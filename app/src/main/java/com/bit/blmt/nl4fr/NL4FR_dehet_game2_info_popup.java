package com.bit.blmt.nl4fr;

import android.content.res.AssetManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class NL4FR_dehet_game2_info_popup extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nl4_fr_dehet_game2_info_popup);
        TextView rules = (TextView) findViewById(R.id.tv_game2_rules);
        String Fulltext = "";
        // Read the contents of our asset
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(
                    new InputStreamReader(getAssets().open("de_het_game2_rules.txt")));

            // do reading, usually loop until end of file reading
            String mLine;

            while ((mLine = reader.readLine()) != null) {
                //process line
                //mLine = mLine.replaceAll("\\n",System.getProperty("line.separator"));
                rules.append(mLine + '\n');
            }

        } catch (IOException e) {
            //log the exception
            Fulltext += e.toString();
        }
        //rules.setText(Fulltext);
    }

    public void rules_read_ok(View view)
    {
        finish();
    }
}
