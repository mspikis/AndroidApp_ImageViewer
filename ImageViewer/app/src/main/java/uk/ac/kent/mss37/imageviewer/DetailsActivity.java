package uk.ac.kent.mss37.imageviewer;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Explode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ShareActionProvider;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;

public class DetailsActivity extends AppCompatActivity {
    //variables
    public int photoPosition;
    public ImageInfo photo;
    public ImageView imageView;
    public TextView textView;
    private ProgressBar progressBar;
    private FloatingActionButton shareBt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        // Get the intent
        Intent intent = getIntent();
        photoPosition = intent.getIntExtra("PHOTO_POSITION", 0);

        // Retrieve the data object
        photo = NetworkMgr.getInstance(this).imageList.get(photoPosition);

        // Get content
        imageView = (ImageView) findViewById(R.id.imageView);
        textView = (TextView) findViewById(R.id.imageTitle);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        shareBt = (FloatingActionButton) findViewById(R.id.shareButton);

        //Specify title and image
        textView.setText(photo.title);

        // Submit request to download the image
        NetworkMgr netMgr = NetworkMgr.getInstance(this);
        //if image is found get it
        if (photo.url() != null) {
            netMgr.imageLoader.get(photo.url(), imageListener);
        }
        //else display error image
        else
        {
            imageView.setImageResource(R.drawable.notfound);
        }
        //progress bar display
        progressBar.setVisibility(View.VISIBLE);

        //Share button
        shareBt.setOnClickListener(new View.OnClickListener() {
            //When clicked
            public void onClick(View v) {
                //Set type, content, subject and share link
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("text/plain");
                i.putExtra(Intent.EXTRA_TEXT, "Image link: "+"\n\n"+photo.url());
                i.putExtra(android.content.Intent.EXTRA_SUBJECT,
                        "Share Image");
                startActivity(Intent.createChooser(i, "Share"));
            }
        });








    }



    private ImageLoader.ImageListener imageListener = new ImageLoader.ImageListener()
    {
        @Override
                public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate)
        {
         // Here we have received the image and we can show it in the ImageView
            //Hide progress bar

            if (response.getBitmap() != null)
                imageView.setImageBitmap(response.getBitmap());
            findViewById(R.id.progressBar).setVisibility(View.GONE);

        }
        @Override
                public void onErrorResponse(VolleyError error)
        {
            //If image unavailable display responding image
            imageView.setImageResource(R.drawable.notfound);
        }
    };
}
