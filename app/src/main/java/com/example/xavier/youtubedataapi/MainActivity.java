package com.example.xavier.youtubedataapi;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.android.volley.Response;

import java.util.List;



public class MainActivity extends AppCompatActivity {

    public static final String API_KEY = "AIzaSyAmhAnl4yEtL9ConqsjbdZohR6bXsmNQ08";
    public static final String URL = "https://www.googleapis.com/youtube/v3/search?part=snippet";
    public static final long NUMBER_VIDEOS = 10;

    private ResponseVideo videoResponse;
    private TextView textViewSearch;
    private ListView listView;
    private Button searchButton;
    private List<ResponseVideo.ItemsBean> listVideos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView) findViewById(R.id.ListVideos);
        searchButton = (Button) findViewById(R.id.searchButton);
        textViewSearch = (TextView) findViewById(R.id.textVideo);
        getVideos("");
        searchButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                getVideos(textViewSearch.getText().toString());
            }
        });

    }

    private void getVideos(String queryString) {
        String stringReadyForQuery = TransformStringQuery(queryString);
        String query="";
        Log.d("TEST",URL + "&q=" + stringReadyForQuery + "&maxResults="+NUMBER_VIDEOS+"&type=video&key=" + API_KEY);
        if(queryString == "")
            query = URL + "&order=date&relevanceLanguage=fr&key=" + API_KEY;
        else
            query = URL + "&q=" + stringReadyForQuery + "&maxResults="+NUMBER_VIDEOS+ "&key=" + API_KEY;

        StringRequest videosRequest = new StringRequest(query, new Response.Listener<String>() {


            @Override
            public void onResponse(String response) {
                videoResponse = new Gson().fromJson(response, ResponseVideo.class);
                listVideos = videoResponse.getItems();
                setAdapter(videoResponse);
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Contracts", "Error");
            }
        });

        Volley.newRequestQueue(this).add(videosRequest);
    }

    private String TransformStringQuery(String queryString){
        String stringTransformed;
        stringTransformed = queryString.replace(' ','|');
        return stringTransformed;
    }

    private void setAdapter(ResponseVideo videoResponse) {
        Adapter adapter = new Adapter(this, listVideos);
        listView.setAdapter(adapter);
    }


}
