package com.yoonhs3434.suroom;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Main2Activity extends AppCompatActivity {

    Button choice_subject_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        choice_subject_btn = (Button) findViewById(R.id.choiceSubjectButton);
    }

    public void choiceSubjectClicked(View v){
        Intent intent = new Intent(getApplicationContext(), SubjectChoiceActivity.class);
        startActivity(intent);
    }
}
