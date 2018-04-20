package com.yacob.weekend;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;

public class Conditions extends AppCompatActivity {
    TextView title,body;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conditions);
        title = findViewById(R.id.title);
        body = findViewById(R.id.body);
        Intent intent = getIntent();
        String from =intent.getStringExtra("from");
        if (from.equals("safety")){
            title.setText("قواعد الامن و السلامه");
            body.setText(R.string.safe);

        }else {
            title.setText("الأحكام و الشروط");
            body.setText(R.string.conditions);
            body.setMovementMethod(new ScrollingMovementMethod());

        }
    }
}
