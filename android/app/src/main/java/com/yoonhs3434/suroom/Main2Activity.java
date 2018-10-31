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
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.yoonhs3434.suroom.GroupMatch.ItemGroup;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

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
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main2);

        choice_subject_btn = (Button) findViewById(R.id.choiceSubjectButton);
        choice_tendency_btn = (Button) findViewById(R.id.choiceTendencyButton);
        // matching_btn = (Button) findViewById(R.id.matchingButton);
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
            holder.title.setText(mItems.get(i).title);
            holder.description.setText(mItems.get(i).description);
            holder.numPeople.setText(Integer.toString(mItems.get(i).numPeople));
            holder.maxNumPeople.setText(Integer.toString(mItems.get(i).maxNumPeople));
            for(int j=0; j<holder.tag.length; j++){
                holder.tag[j].setText(mItems.get(i).tags[j]);
            }

            // setAnimation(holder.imageView, i);
        }

        @Override
        public int getItemCount() {
            return mItems.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder{
            public TextView title, description, numPeople, maxNumPeople;
            public TextView [] tag = new TextView[5];

            public ViewHolder(View v){
                super(v);
                title = (TextView) v.findViewById(R.id.textGroupTitle);
                description = (TextView) v.findViewById(R.id.textGroupDescription);
                numPeople = (TextView) v.findViewById(R.id.textNumPeople);
                maxNumPeople = (TextView) v.findViewById(R.id.textMaxNumPeople);
                tag[0] = (TextView) v.findViewById(R.id.textTag1);
                tag[1] = (TextView) v.findViewById(R.id.textTag2);
                tag[2] = (TextView) v.findViewById(R.id.textTag3);
                tag[3] = (TextView) v.findViewById(R.id.textTag4);
                tag[4] = (TextView) v.findViewById(R.id.textTag5);
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

            String title;
            String description;
            int numPeople;
            int maxNumPeople;
            String [] tag = new String[5];

            try {
                for(int i=0; i<result.length(); i++){
                    title = result.getJSONObject(i).getString("name");
                    description = result.getJSONObject(i).getString("description");
                    numPeople = result.getJSONObject(i).getInt("num_people");
                    maxNumPeople = result.getJSONObject(i).getInt("max_num_people");
                    for(int j=0; j<tag.length; j++){
                        tag[j] = " ";
                        String temp = result.getJSONObject(i).getString("tag"+Integer.toString(j+1));
                        if(temp.equals("null")) {
                            tag[j] = " ";
                        }
                        else
                            tag[j] = "# "+temp;
                    }
                    items.add(new ItemGroup(title, description, maxNumPeople, numPeople, tag));
                }

                adapter = new MyAdapter(mContext, items);
                group_list.setAdapter(adapter);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
