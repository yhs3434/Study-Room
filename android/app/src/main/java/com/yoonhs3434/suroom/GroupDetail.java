package com.yoonhs3434.suroom;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;

public class GroupDetail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_group_detail);
    }
}
