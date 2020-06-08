package com.parse.starter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FeedActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);
        setTitle("Your feed");

        final ListView listView=findViewById(R.id.listView);
        final List<Map<String,String>> blogData=new ArrayList<>();

        ParseQuery<ParseObject> query=ParseQuery.getQuery("Blog");
        query.whereContainedIn("username", ParseUser.getCurrentUser().getList("isFollowing"));
        query.orderByDescending("createdAt");
        query.setLimit(20);

        query.findInBackground(new FindCallback<ParseObject>() {
            @Override
            public void done(List<ParseObject> objects, ParseException e) {
                if(e==null){
                    for(ParseObject blog:objects){
                        Map<String,String> blogInfo=new HashMap<>();
                        blogInfo.put("content",blog.getString("blog"));
                        blogInfo.put("username",blog.getString("username"));
                        blogData.add(blogInfo);
                    }
                    SimpleAdapter simpleAdapter=new SimpleAdapter(FeedActivity.this,blogData,android.R.layout.simple_list_item_2,new String[]{"content","username"},new int[]{android.R.id.text1,android.R.id.text2});
                    listView.setAdapter(simpleAdapter);
                }
            }
        });


    }
}
