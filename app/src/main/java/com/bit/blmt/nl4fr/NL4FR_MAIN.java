package com.bit.blmt.nl4fr;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class NL4FR_MAIN extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nl4_fr__main);
    }

    public void oc_dehet(View view)
    {
        Intent nextAction = new Intent(this,NL4FR_dehet_main.class);
        startActivity(nextAction);
    }
}
