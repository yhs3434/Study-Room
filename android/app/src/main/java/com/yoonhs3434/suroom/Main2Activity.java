package com.yoonhs3434.suroom;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.yoonhs3434.suroom.GroupMatch.ItemGroup;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;

public class Main2Activity extends AppCompatActivity {

    Button choice_subject_btn, choice_tendency_btn, matching_btn;
    RecyclerView group_list;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;
    ArrayList items;
    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        choice_subject_btn = (Button) findViewById(R.id.choiceSubjectButton);
        choice_tendency_btn = (Button) findViewById(R.id.choiceTendencyButton);
        matching_btn = (Button) findViewById(R.id.matchingButton);
        group_list = (RecyclerView) findViewById(R.id.groupList);
        group_list.setHasFixedSize(true);
        items = new ArrayList<>();

        mContext = getApplicationContext();

        layoutManager = new LinearLayoutManager(this);
        group_list.setLayoutManager(layoutManager);
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
    }

    public void getGroupListClicked(View v){
        String [] params = new String[1];
        params[0] = MySetting.getMyUrl()+"group/";

        HttpGetRequest myHttp = new HttpGetRequest();
        myHttp.execute(params);

        ArrayList testItems = new ArrayList<>();
        testItems.add(new ItemGroup("test", "des"));
        testItems.add(new ItemGroup("test2", "des2"));

        // adapter = new MyAdapter(mContext, testItems);
        // group_list.setAdapter(adapter);
    }

    // Adapter class
    class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder>{
        private Context context;
        private ArrayList<ItemGroup> mItems;

        private int lastPosition = -1;

        public MyAdapter(Context context, ArrayList mItems) {
            this.context = context;
            this.mItems = mItems;
        }

        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_item_group, parent, false);
            ViewHolder holder = new ViewHolder(v);
            return holder;
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int i) {
            holder.text_title.setText(mItems.get(i).title);
            holder.text_description.setText(mItems.get(i).description);

            // setAnimation(holder.imageView, i);
        }

        @Override
        public int getItemCount() {
            return mItems.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder{
            public TextView text_title, text_description;

            public ViewHolder(View v){
                super(v);
                text_title = (TextView) v.findViewById(R.id.textGroupTitle);
                text_description = (TextView) v.findViewById(R.id.textGroupDescription);
            }
        }

        /*
        private void setAnimation(View viewToAnimate, int i){
            if(i > lastPosition){
                Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
                viewToAnimate.startAnimation(animation);
                lastPosition = i;
            }
        }
        */
    }

    class HttpGetRequest extends AsyncTask<String, Void, JSONArray> {

        String REQUEST_METHOD = "GET";
        int READ_TIMEOUT = 15000;
        int CONNECTION_TIMEOUT = 15000;

        @Override
        protected JSONArray doInBackground(String... strings) {
            String stringUrl = strings[0];
            JSONArray result = null;
            String inputLine;
            String stringResult;

            try {
                URL myUrl = new URL(stringUrl);
                HttpURLConnection connection = (HttpURLConnection) myUrl.openConnection();

                connection.setRequestMethod(REQUEST_METHOD);
                connection.setReadTimeout(READ_TIMEOUT);
                connection.setConnectTimeout(CONNECTION_TIMEOUT);

                connection.connect();

                InputStreamReader streamReader = new InputStreamReader(connection.getInputStream());
                BufferedReader reader = new BufferedReader(streamReader);
                StringBuilder stringBuilder = new StringBuilder();

                while ((inputLine = reader.readLine()) != null) {
                    stringBuilder.append(inputLine);
                }

                reader.close();
                streamReader.close();

                stringResult = stringBuilder.toString();
                result = new JSONArray(stringResult);

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return result;
        }

        @Override
        protected void onPostExecute(JSONArray result) {
            super.onPostExecute(result);
            items = null;
            items = new ArrayList<>();

            try {
                for(int i=0; i<result.length(); i++){

                    items.add(new ItemGroup(result.getJSONObject(i).getString("name"), result.getJSONObject(i).getString("description")));
                }

                adapter = new MyAdapter(mContext, items);
                group_list.setAdapter(adapter);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
