package com.yoonhs3434.suroom.GroupRoom;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.yoonhs3434.suroom.MySetting;
import com.yoonhs3434.suroom.R;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

public class GroupMain extends AppCompatActivity {

    Button joinButton, meetingButton;
    int groupId;
    TextView nameText, notificationText, meetingText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_main);

        joinButton = (Button) findViewById(R.id.joinButton);
        meetingButton = (Button) findViewById(R.id.meetingButton);
        nameText = (TextView) findViewById(R.id.studyNameText);
        notificationText = (TextView) findViewById(R.id.studyNotificationText);
        meetingText = (TextView) findViewById(R.id.meetingText);

        Intent intent = getIntent();
        groupId = intent.getIntExtra("id", 0);
        if(groupId == 0)
            finish();
        MySetting.setGroupId(groupId);
        MySetting.updateGroup(groupId);

    }

    public void joinButtonClicked(View v){
        Intent intent = new Intent(getApplicationContext(), JoinCheck.class);
        intent.putExtra("group_id", groupId);
        startActivity(intent);
    }
}
