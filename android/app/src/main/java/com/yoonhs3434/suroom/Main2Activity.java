package com.yoonhs3434.suroom;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

public class Main2Activity extends AppCompatActivity {

    Button choice_subject_btn, choice_tendency_btn, matching_btn;
    TextView matchingText, noneText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        choice_subject_btn = (Button) findViewById(R.id.choiceSubjectButton);
        choice_tendency_btn = (Button) findViewById(R.id.choiceTendencyButton);
        matching_btn = (Button) findViewById(R.id.matchingButton);
        matchingText = (TextView) findViewById(R.id.matchingText);
        noneText = (TextView) findViewById(R.id.noneText);
    }

    public void choiceSubjectClicked(View v){
        Intent intent = new Intent(getApplicationContext(), SubjectChoiceActivity.class);
        startActivity(intent);
    }

    public void choiceTendencyClicked(View v){
        Intent intent = new Intent(getApplicationContext(), TendencyChoiceActivity.class);
        startActivity(intent);
    }

    public void matchingClicked(View v) throws JSONException {
        JSONObject req_data = new JSONObject();
        req_data.accumulate("id", MySetting.getMyId());
        HttpPost send = new HttpPost(MySetting.getMyUrl()+"find/group/", req_data);
        send.start();
        try {
            send.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        matchingText.setVisibility(View.VISIBLE);
        noneText.setVisibility(View.INVISIBLE);
    }
}
