package uk.ac.kent.mss37.imageviewer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class MainActivity extends AppCompatActivity {
    // Variables
    private RecyclerView recyclerView;
    private LinearLayoutManager layoutManager;
    private ImageListAdapter adapter;
    Toast toast;






    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize variables
        recyclerView = (RecyclerView) findViewById(R.id.photoListView);
        toast = Toast.makeText(getApplicationContext(),"Loading Error!",Toast.LENGTH_LONG);

        // Set RecyclerView layout
        layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemViewCacheSize(10);
        recyclerView.setDrawingCacheEnabled(true);
        recyclerView.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);

        adapter = new ImageListAdapter(this);
        recyclerView.setAdapter(adapter);


        adapter.imageList = NetworkMgr.getInstance(this).imageList;
        recyclerView = (RecyclerView) findViewById(R.id.photoListView);

        // Get the request queue
        NetworkMgr netMgr = NetworkMgr.getInstance(getApplicationContext());
        RequestQueue requestQueue = netMgr.requestQueue;

        // Create the url
        String url = "https://api.flickr.com/services/rest/?method=flickr.photos.getRecent&api_key=6fedbf3bed33a3c90fb19f86f9fbb613&format=json&nojsoncallback=?&extras=description,owner_name,url_m,url_l,url_o,date_taken&per_page=50";

        // Create the request object for that url
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, responceListener, errorListener);

        // Submit the request to the request queue
        requestQueue.add(request);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu and add items to it.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.options_menu, menu);

        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Get clicked menu item
        int id = item.getItemId();

        // When clicked item is "About" start AboutActivity
        if (id == R.id.action_about) {
            Intent i = new Intent(getApplicationContext(),AboutActivity.class);
            startActivity(i);
        }

        return super.onOptionsItemSelected(item);
    }

    private Response.Listener<JSONObject> responceListener = new Response.Listener<JSONObject>()
    {
        @Override
        public void onResponse(JSONObject response)
        {
            // We successfully received the JSON object
            // Here we extract the data and use it in our app

            try{
                JSONObject photos = response.getJSONObject("photos");
                JSONArray photoList = photos.getJSONArray("photo");
                for (int i=0; i < photoList.length(); i++)
                {
                    ImageInfo newImage = new ImageInfo();

                    JSONObject photo = photoList.getJSONObject(i);

                    newImage.id = photo.getString("id");
                    newImage.title = photo.getString("title");
                    newImage.owner = photo.getString("owner");
                    newImage.description = photo.getString("description");
                    if ( photo.has("url_m")) {
                        newImage.url_m = photo.getString("url_m");
                    }
                    if ( photo.has("url_l")) {
                        newImage.url_l = photo.getString("url_l");
                    }
                    if ( photo.has("url_o")) {
                        newImage.url_o = photo.getString("url_o");
                    }

                    NetworkMgr.getInstance(MainActivity.this).imageList.add(newImage);

                }
            }
            catch (JSONException ex)
            {
                // Something went wrong when parsing the json data
                ex.printStackTrace();
            }
            adapter.notifyDataSetChanged();
        }
    };
    private Response.ErrorListener errorListener = new Response.ErrorListener()
    {
        // When there is a loading error
        @Override
        public void onErrorResponse (VolleyError error)
        {
            // Display Error Message
            toast.show();
        }

    };
}
