package com.bit.blmt.nl4fr;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class NL4FR_dehet_main extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nl4_fr_dehet_main);
    }

    public void oc_start_game1(View view)
    {

    }

    public void oc_start_game2(View view)
    {

    }

    public void go_progressive(View view)
    {
        System.out.println("Progressive Clicked");
        Intent nextAction = new Intent(this,NL4FR_dehet_game2.class);
        startActivity(nextAction);
    }
}
